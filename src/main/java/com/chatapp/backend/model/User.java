package com.chatapp.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(name = "user_code_idx")
    private String userCode;

    private String name;

    @Indexed(name = "email_idx")
    private String email;

    private String password;

    // New Fields
    private String bio;
    private String status;
    private String profilePic;
}