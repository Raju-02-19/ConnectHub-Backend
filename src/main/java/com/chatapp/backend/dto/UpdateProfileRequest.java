package com.chatapp.backend.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String name;
    private String bio;
    private String status;
    private String profilePic;
}