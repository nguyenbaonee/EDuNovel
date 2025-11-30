package com.example.common.event;

import java.util.Map;

import com.example.common.enums.TemplateCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendNotificationEvent {
    private String channel;
    private String recipient;
    private TemplateCode templateCode;
    private Map<String, Object> param;
}
