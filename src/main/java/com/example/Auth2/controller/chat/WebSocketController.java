package com.example.Auth2.controller.chat;

import com.example.Auth2.DTO.MessageDto;
import com.example.Auth2.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    public WebSocketController(SimpMessagingTemplate messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    @MessageMapping("/chat")
    public void sendMessage(@Payload MessageDto message, Authentication auth) {
        String sender = auth.getName();
        chatService.saveMessage(sender, message.to(), message.text());

        messagingTemplate.convertAndSend(
                "/topic/messages",
                new MessageDto(sender, message.to(), message.text())
        );
    }
}
