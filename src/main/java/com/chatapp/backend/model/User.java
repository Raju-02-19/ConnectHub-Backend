package com.chatapp.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String userCode;

    private String name;
    private String email;
    private String password;

    // New Fields
    private String bio;
    private String status;
    private String profilePic;
}