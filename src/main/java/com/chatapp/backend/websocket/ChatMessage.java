package com.chatapp.backend.websocket;

import lombok.Data;

@Data
public class ChatMessage {

    private String senderId;
    private String receiverId;
    private String message;
    private Long timestamp;
}