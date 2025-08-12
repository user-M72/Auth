package com.example.Auth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/v1")
public class TestController {

    @GetMapping("/all")
    public String allAccess(){
        return "Public Content";
    }

    @GetMapping("/user")
    public String userAccess(){
        return "User content";
    }
}
