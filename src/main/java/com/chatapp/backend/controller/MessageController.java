package com.chatapp.backend.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.backend.model.Message;
import com.chatapp.backend.repository.FriendRepository;
import com.chatapp.backend.repository.MessageRepository;
import com.chatapp.backend.websocket.ChatMessage;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin("*")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Send Message
    @PostMapping("/send")
    public String sendMessage(@RequestBody Message message) {

        boolean isFriend = friendRepository.existsByUser1AndUser2OrUser1AndUser2(
                message.getSenderId(),
                message.getReceiverId(),
                message.getReceiverId(),
                message.getSenderId());

        if (!isFriend) {
            return "You are not friends";
        }

        message.setTimestamp(LocalDateTime.now());
        message.setStatus("SENT");

        // Save in MongoDB
        messageRepository.save(message);

        // Broadcast via WebSocket
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderId(message.getSenderId());
        chatMessage.setReceiverId(message.getReceiverId());
        chatMessage.setMessage(message.getMessage());
        chatMessage.setTimestamp(System.currentTimeMillis());

        messagingTemplate.convertAndSend(
                "/topic/messages",
                chatMessage);

        return "Message Sent";
    }

    // Get All Messages
    @GetMapping("/all")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Get Chat History
    @GetMapping("/chat/{user1}/{user2}")
    public List<Message> getChatHistory(
            @PathVariable String user1,
            @PathVariable String user2) {

        return messageRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(
                user1,
                user2,
                user2,
                user1);
    }

    // Get Unread Count
    @GetMapping("/unread/{senderId}/{receiverId}")
    public long getUnreadCount(
            @PathVariable String senderId,
            @PathVariable String receiverId) {

        return messageRepository.countBySenderIdAndReceiverIdAndStatus(
                senderId,
                receiverId,
                "SENT");
    }

    // Mark Messages as Read
    @PutMapping("/read/{senderId}/{receiverId}")
    public String markAsRead(
            @PathVariable String senderId,
            @PathVariable String receiverId) {

        List<Message> messages =
                messageRepository.findBySenderIdAndReceiverId(
                        senderId,
                        receiverId);

        List<Message> updatedMessages = new ArrayList<>();

        for (Message msg : messages) {

            if (msg.getReceiverId().equals(receiverId)) {

                msg.setStatus("READ");
                updatedMessages.add(msg);
            }
        }

        if (!updatedMessages.isEmpty()) {
            messageRepository.saveAll(updatedMessages);
        }

        return "Messages Marked As Read";
    }
}