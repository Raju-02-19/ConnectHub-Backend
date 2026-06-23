package com.chatapp.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatapp.backend.model.FriendRequest;

public interface FriendRequestRepository extends MongoRepository<FriendRequest, String> {

    List<FriendRequest> findByReceiverId(String receiverId);

    List<FriendRequest> findBySenderId(String senderId);
}