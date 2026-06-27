
package com.chatapp.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatapp.backend.model.Message;

public interface MessageRepository
                extends MongoRepository<Message, String> {

        List<Message> findBySenderIdAndReceiverId(String senderId, String receiverId);

        List<Message> findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(
                        String sender1,
                        String receiver1,
                        String sender2,
                        String receiver2);

        List<Message> findBySenderIdOrReceiverId(
                        String senderId,
                        String receiverId);

        long countBySenderIdAndReceiverIdAndStatus(
                        String senderId,
                        String receiverId,
                        String status);
}

