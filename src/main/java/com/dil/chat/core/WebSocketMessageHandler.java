package com.dil.chat.core;

/**
 * 消息处理器接口
 *      接口定义消息处理策略，不同的业务实现各自的处理逻辑。
 */
public interface WebSocketMessageHandler {
    void handleMessage(String message, String sender, String receiver, String roomId);
    void handleConnect(String userId, String roomId);
    void handleDisconnect(String userId, String roomId);
}