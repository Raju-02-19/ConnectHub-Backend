package com.chatapp.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "friends")
@CompoundIndex(name = "user1_user2_idx", def = "{ 'user1' : 1, 'user2' : 1 }")
public class Friend {

    @Id
    private String id;

    @Indexed(name = "user1_idx")
    private String user1;

    @Indexed(name = "user2_idx")
    private String user2;
}