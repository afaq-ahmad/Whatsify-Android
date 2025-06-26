package com.codespacepro.whatsify.Models;

public class Message {
    private String messageId;
    private String senderId;
    private String text;
    private long timestamp;
    private int status; // 0 sent, 1 delivered, 2 read

    public Message() {
    }

    public Message(String senderId, String text, long timestamp, int status) {
        this.senderId = senderId;
        this.text = text;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
