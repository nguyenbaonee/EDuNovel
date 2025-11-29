package com.example.ai.service;

import com.example.ai.config.OpenAIConfig;
import com.example.ai.entity.ChatLog;
import com.example.ai.repo.ChatLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiChatService {

    private final ChatLogRepository chatLogRepository;
    private final OpenAIConfig openAIConfig;
    private final WebClient webClient;

    public AiChatService(ChatLogRepository chatLogRepository, OpenAIConfig openAIConfig, WebClient.Builder webClientBuilder) {
        this.chatLogRepository = chatLogRepository;
        this.openAIConfig = openAIConfig;
        this.webClient = webClientBuilder.baseUrl(openAIConfig.getApiUrl()).build();
    }

    public String chat(Integer userId, String messageText) {
        // Lưu tin nhắn user
        ChatLog userMessage = new ChatLog();
        userMessage.setUserId(userId);
        userMessage.setMessageText(messageText);
        chatLogRepository.save(userMessage);

        // Chuẩn bị request cho OpenAI
        Map<String, Object> body = new HashMap<>();
        body.put("model", openAIConfig.getModel());
        body.put("messages", List.of(Map.of("role", "user", "content", messageText)));
        body.put("max_tokens", 1000);

        // Gọi API OpenAI
        Map response = webClient.post()
                .header("Authorization", "Bearer " + openAIConfig.getApiKey())
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        // Lấy reply từ API response
        String reply = ((Map)((List)response.get("choices")).get(0)).get("message").toString();

        // Lưu reply vào DB
        userMessage.setReply(reply);
        chatLogRepository.save(userMessage);

        return reply;
    }
}

