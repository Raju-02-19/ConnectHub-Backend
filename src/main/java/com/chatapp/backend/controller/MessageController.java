
package com.chatapp.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.backend.model.Friend;
import com.chatapp.backend.model.Message;
import com.chatapp.backend.repository.FriendRepository;
import com.chatapp.backend.repository.MessageRepository;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin("*")
public class MessageController {

        @Autowired
        private MessageRepository messageRepository;

        @Autowired
        private FriendRepository friendRepository;

        @PostMapping("/send")
        public String sendMessage(
                        @RequestBody Message message) {

                List<Friend> friends1 = friendRepository.findByUser1(
                                message.getSenderId());

                List<Friend> friends2 = friendRepository.findByUser2(
                                message.getSenderId());

                boolean isFriend = false;

                for (Friend f : friends1) {

                        if (f.getUser2()
                                        .equals(
                                                        message.getReceiverId())) {

                                isFriend = true;
                        }
                }

                for (Friend f : friends2) {

                        if (f.getUser1()
                                        .equals(
                                                        message.getReceiverId())) {

                                isFriend = true;
                        }
                }

                if (!isFriend) {
                        return "You are not friends";
                }

                message.setTimestamp(
                                LocalDateTime.now());

                message.setStatus(
                                "SENT");

                messageRepository.save(
                                message);

                return "Message Sent";
        }

        @GetMapping("/all")
        public List<Message> getAllMessages() {

                return messageRepository.findAll();
        }

        @GetMapping("/chat/{user1}/{user2}")
        public List<Message> getChatHistory(
                        @PathVariable String user1,
                        @PathVariable String user2) {

                return messageRepository
                                .findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(
                                                user1,
                                                user2,
                                                user2,
                                                user1);
        }

        @GetMapping("/unread/{senderId}/{receiverId}")
        public long getUnreadCount(
                        @PathVariable String senderId,
                        @PathVariable String receiverId) {

                return messageRepository
                                .countBySenderIdAndReceiverIdAndStatus(
                                                senderId,
                                                receiverId,
                                                "SENT");
        }

        @PutMapping("/read/{senderId}/{receiverId}")
        public String markAsRead(
                        @PathVariable String senderId,
                        @PathVariable String receiverId) {

                List<Message> messages = messageRepository
                                .findBySenderIdAndReceiverId(
                                                senderId,
                                                receiverId);

                for (Message msg : messages) {

                        if (msg.getReceiverId()
                                        .equals(receiverId)) {

                                msg.setStatus(
                                                "READ");

                                messageRepository.save(
                                                msg);
                        }
                }

                return "Messages Marked As Read";
        }
}
