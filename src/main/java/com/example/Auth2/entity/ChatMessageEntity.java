package com.example.Auth2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "chats")
@Data
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromUser;   // кто отправил
    private String toUser;     // кому отправлено, null = общий чат
    private String text;

    private LocalDateTime timestamp;

}
