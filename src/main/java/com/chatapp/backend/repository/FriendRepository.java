package com.chatapp.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatapp.backend.model.Friend;

public interface FriendRepository
        extends MongoRepository<Friend, String> {

    List<Friend> findByUser1(String user1);

    List<Friend> findByUser2(String user2);

    boolean existsByUser1AndUser2(
            String user1,
            String user2);
}