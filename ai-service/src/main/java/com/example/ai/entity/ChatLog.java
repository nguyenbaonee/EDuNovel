package com.example.ai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_logs")
@Data
@Builder
@AllArgsConstructor
public class ChatLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "message_text", columnDefinition = "TEXT")
    private String messageText;

    @Column(name = "reply", columnDefinition = "TEXT")
    private String reply;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

     @Column(name = "conversation_id")
     private Integer conversationId;

     @Column(name = "role")
     private String role; // "user" hoặc "assistant"

    public ChatLog() {
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
