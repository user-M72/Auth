package com.example.Auth2.repository;

import com.example.Auth2.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessageEntity, Long> {

    List<ChatMessageEntity> findByToUserIsNullOrderByTimestampAsc();


    @Query("SELECT m FROM ChatMessageEntity m " +
            "WHERE (m.fromUser = :user1 AND m.toUser = :user2) " +
            "   OR (m.fromUser = :user2 AND m.toUser = :user1) " +
            "ORDER BY m.timestamp ASC")
    List<ChatMessageEntity> findPrivateMessages(
            @Param("user1") String user1,
            @Param("user2") String user2
    );
}
