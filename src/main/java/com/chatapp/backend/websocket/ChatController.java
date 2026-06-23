package com.chatapp.backend.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(
            ChatMessage message) {

        message.setTimestamp(
                System.currentTimeMillis());

        messagingTemplate.convertAndSend(
                "/topic/messages",
                message);
    }
}