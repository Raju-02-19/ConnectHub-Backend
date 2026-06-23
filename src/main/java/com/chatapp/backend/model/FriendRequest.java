package com.chatapp.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "friend_requests")
public class FriendRequest {

    @Id
    private String id;

    private String senderId;
    private String receiverId;

    private String status; // PENDING, ACCEPTED, REJECTED
}