package com.dil.chat.model;

public class MessageDTO {
    private String content;
    private String receiver;  // 接收者，为空表示群发

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
} 