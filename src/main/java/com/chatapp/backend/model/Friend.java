package com.chatapp.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "friends")
public class Friend {

    @Id
    private String id;

    private String user1;
    private String user2;
}