package com.example.Auth2.controller;

import com.example.Auth2.DTO.MessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketController {

    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public MessageDto sendMessage(@Payload MessageDto message, Principal principal){
        return new MessageDto(principal.getName(), message.text().trim());
    }
}
