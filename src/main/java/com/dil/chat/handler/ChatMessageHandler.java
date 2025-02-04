package com.dil.chat.handler;

import com.dil.chat.core.WebSocketMessageHandler;
import com.dil.chat.model.ChatMessage;
import com.dil.chat.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("chat")
public class ChatMessageHandler implements WebSocketMessageHandler {

    private final Logger logger = LoggerFactory.getLogger(ChatMessageHandler.class);

    @Autowired
    private ChatService chatService;
    
    @Override
    public void handleMessage(String message, String sender, String receiver, String roomId) {
        logger.info("发送者：{}，接收者： {}，消息：{} ，roomId： {}", sender, receiver, message, roomId);
        ChatMessage.MessageType type = receiver == null || receiver.isEmpty() ?
                ChatMessage.MessageType.PUBLIC : ChatMessage.MessageType.PRIVATE;
        chatService.saveMessage(sender, receiver, message, roomId, type);
    }
    
    @Override
    public void handleConnect(String userId, String roomId) {
        logger.info("用户 {} 加入房间 {}", userId, roomId);
    }
    
    @Override
    public void handleDisconnect(String userId, String roomId) {
        logger.info("用户 {} 离开房间 {}", userId, roomId);
    }

} 