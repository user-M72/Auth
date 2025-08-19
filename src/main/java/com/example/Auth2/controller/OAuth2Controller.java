package com.example.Auth2.controller;

import com.example.Auth2.entity.User;
import com.example.Auth2.repository.UserRepository;
import com.example.Auth2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class OAuth2Controller {

    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;


    @GetMapping("/user")
    public String user(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                       Model model) {
        if (user != null) {
            User dbUser = userRepository.findByEmail(user.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            model.addAttribute("name", dbUser.getUsername());
            model.addAttribute("email", dbUser.getEmail());
        }
        return "user";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String showLogin(){
        return null;
    }
}
