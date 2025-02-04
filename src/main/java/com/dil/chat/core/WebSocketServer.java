package com.dil.chat.core;

import com.dil.chat.model.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: "xx"
 * @Date: 2025/2/2
 */
@Component
@ServerEndpoint("/websocket/chat/{userId}/{roomId}")
public class WebSocketServer {

    private static Map<String, WebSocketMessageHandler> messageHandlers;

    @Resource
    public void setMessageHandlers(Map<String, WebSocketMessageHandler> handlers) {
        WebSocketServer.messageHandlers = handlers;
    }

    private static final Map<String, List<Session>> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session,
                       @PathParam("userId") String userId,
                       @PathParam("roomId") String roomId) {
        String key = String.format("%s_%s", userId, roomId);
        sessions.computeIfAbsent(key, k -> new ArrayList<>()).add(session);

        // 通知业务处理器
        WebSocketMessageHandler handler = messageHandlers.get("chat");
        if (handler != null) {
            handler.handleConnect(userId, roomId);
        }

    }

    @OnMessage
    public void onMessage(String message,
                          @PathParam("userId") String userId,
                          @PathParam("roomId") String roomId) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            MessageDTO messageDTO = mapper.readValue(message, MessageDTO.class);

            // 通知业务处理器
            WebSocketMessageHandler handler = messageHandlers.get("chat");
            if (handler != null) {
                handler.handleMessage(messageDTO.getContent(), userId,
                        messageDTO.getReceiver(), roomId);
            }

            // 如果有指定接收者，发送私聊消息
            if (messageDTO.getReceiver() != null && !messageDTO.getReceiver().isEmpty()) {
                sendPrivateMessage(messageDTO.getContent(), userId,
                        messageDTO.getReceiver(), roomId);
            } else {
                // 否则广播消息
                broadcastMessage(messageDTO.getContent(), userId, roomId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session,
                        @PathParam("userId") String userId,
                        @PathParam("roomId") String roomId) {
        String key = String.format("%s_%s", userId, roomId);
        List<Session> userSessions = sessions.get(key);
        if (userSessions != null) {
            userSessions.remove(session);
            if (userSessions.isEmpty()) {
                sessions.remove(key);

                // 通知业务处理器
                WebSocketMessageHandler handler = messageHandlers.get("chat");
                if (handler != null) {
                    handler.handleDisconnect(userId, roomId);
                }

            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    private void broadcastMessage(String message, String sender, String roomId) {
        sessions.forEach((key, userSessions) -> {
            if (key.endsWith("_" + roomId)) {
                userSessions.forEach(session -> {
                    try {
                        if (session.isOpen()) {
                            session.getBasicRemote().sendText(sender + ": " + message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private void sendPrivateMessage(String message, String sender,
                                    String receiver, String roomId) {
        String receiverKey = String.format("%s_%s", receiver, roomId);
        List<Session> receiverSessions = sessions.get(receiverKey);
        String senderKey = String.format("%s_%s", sender, roomId);
        List<Session> senderSessions = sessions.get(senderKey);

        // 发送给接收者
        if (receiverSessions != null) {
            receiverSessions.forEach(session -> {
                try {
                    if (session.isOpen()) {
                        session.getBasicRemote().sendText(
                                String.format("[私聊]%s: %s", sender, message));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        // 同时发送给发送者（回显）
        if (senderSessions != null) {
            senderSessions.forEach(session -> {
                try {
                    if (session.isOpen()) {
                        session.getBasicRemote().sendText(
                                String.format("[私聊发送给%s]: %s", receiver, message));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public List<String> getOnlineUsers(String roomId) {
        List<String> onlineUsers = new ArrayList<>();
        String suffix = "_" + roomId;

        sessions.forEach((key, userSessions) -> {
            if (key.endsWith(suffix) && !userSessions.isEmpty()) {
                String userId = key.substring(0, key.length() - suffix.length());
                onlineUsers.add(userId);
            }
        });
        return onlineUsers;
    }
}
