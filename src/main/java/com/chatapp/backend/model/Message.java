package com.chatapp.backend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "messages")
@CompoundIndex(name = "sender_receiver_status_idx", def = "{ 'senderId' : 1, 'receiverId' : 1, 'status' : 1 }")
@CompoundIndex(
    name = "chat_time_idx",
    def = "{ 'senderId': 1, 'receiverId': 1, 'timestamp': -1 }"
)
public class Message {

    @Id
    private String id;

    @Indexed(name = "sender_idx")
    private String senderId;

    @Indexed(name = "receiver_idx")
    private String receiverId;

    private String message;

    private LocalDateTime timestamp;

    @Indexed(name = "status_idx")
    private String status;
}