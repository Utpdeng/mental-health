package com.dil.chat.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "chat_messages")
public class ChatMessage {
    @Id
    private String id;
    private String sender;
    private String receiver;
    private String content;
    private Date timestamp;
    private String roomId;
    private MessageType type;

    public enum MessageType {
        PUBLIC,     // 公共消息
        PRIVATE     // 私聊消息
    }

    public ChatMessage() {
        this.timestamp = new Date();
        this.type = MessageType.PRIVATE; // 默认为私聊消息
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
} 