package com.example.Auth2.controller.chat;

import com.example.Auth2.entity.User;
import com.example.Auth2.repository.UserRepository;
import com.example.Auth2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public List<String> getAllUsers(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Нет токена!");
        }
        String token = authHeader.substring(7);
        String username = jwtUtil.getUsernameFromToken(token); // нужно Autowired JwtUtil

        return userRepository.findAll().stream()
                .map(User::getUsername)
                .filter(u -> !u.equals(username))
                .toList();
    }
}
