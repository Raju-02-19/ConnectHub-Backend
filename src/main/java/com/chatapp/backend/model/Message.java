package com.chatapp.backend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "messages")
public class Message {

    @Id
    private String id;

    private String senderId;
    private String receiverId;
    private String message;

    private LocalDateTime timestamp;

    private String status;
}