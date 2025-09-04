package com.example.Auth2.controller.chat;

import com.example.Auth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/chat")
    public String chatPage(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "chat";
    }
}
