package com.example.ai.repo;

import com.example.ai.entity.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLogRepository extends JpaRepository<ChatLog, Integer> {
}
