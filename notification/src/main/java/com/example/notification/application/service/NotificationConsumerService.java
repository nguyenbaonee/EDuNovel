package com.example.notification.application.service;

import com.example.common.enums.Channel;
import com.example.common.event.SendNotificationEvent;
import com.example.notification.application.mapper.TemplateCodeMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.notification.application.service.send.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumerService {
    private final EmailService emailService;
    private final TemplateCodeMapper templateCodeMapper;

    @KafkaListener(topics = "send-noti-group")
    public void handleSendNotification(SendNotificationEvent event) {
        try {
            if (Channel.EMAIL.name().equals(event.getChannel())) {
                processEmailNotification(event);
            } else if (Channel.SMS.name().equals(event.getChannel())) {
                // Gửi tin nhắn SMS
            } else if (Channel.PUSH_NOTIFICATION.name().equals(event.getChannel())) {
                // Gửi thông báo push
            }
        } catch (Exception e) {
            log.error("Lỗi xử lý thông báo: {}", e.getMessage(), e);
        }
    }

    private void processEmailNotification(SendNotificationEvent event) {
        // Map templateCode từ event sang template name của Thymeleaf
        String templateName = templateCodeMapper.mapToTemplateName(event.getTemplateCode());

        // Lấy subject dựa vào templateCode
        String subject = templateCodeMapper.getSubject(event.getTemplateCode());

        // Gửi email với template Thymeleaf
        emailService.sendTemplateEmail(event.getRecipient(), subject, templateName, event.getParam());
    }
}
