package com.example.Auth2.service;

import com.example.Auth2.entity.ChatMessageEntity;
import com.example.Auth2.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private  ChatRepository chatMessageRepository;

    public ChatMessageEntity saveMessage(String fromUser, String toUser, String text) {
        ChatMessageEntity message = new ChatMessageEntity();
        message.setFromUser(fromUser);
        message.setToUser(toUser); // null = общий чат
        message.setText(text);
        message.setTimestamp(LocalDateTime.now());
        return chatMessageRepository.save(message);
    }

    // Получить все сообщения для общего чата
    public List<ChatMessageEntity> getPublicMessages() {
        return chatMessageRepository.findByToUserIsNullOrderByTimestampAsc();
    }

    // Получить приватные сообщения между двумя пользователями
    public List<ChatMessageEntity> getPrivateMessages(String user1, String user2) {
        return chatMessageRepository.findPrivateMessages(user1, user2);
    }

}
