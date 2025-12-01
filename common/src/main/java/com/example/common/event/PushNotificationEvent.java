package com.example.common.event;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushNotificationEvent {
    private String title;
    private String body;
    private String imageUrl;
    private String topic;
    private String userId;
    private String token;
    private Map<String, String> data;
}
