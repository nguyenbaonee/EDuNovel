package com.example.order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private String id;
    private String transactionId;
    private String orderId;
    private String orderCode;
    private String paymentUrl;
    private String status;
}