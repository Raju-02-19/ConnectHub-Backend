package com.chatapp.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatapp.backend.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

    User findByUserCode(String userCode);
}