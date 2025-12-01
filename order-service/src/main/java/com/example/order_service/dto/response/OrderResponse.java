package com.example.order_service.dto.response;

import com.example.order_service.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private String id;
    private String orderCode;
    private String userId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private String paymentTransactionId;
    private List<OrderItemResponse> items;
    private String shippingAddress;
    private String phoneNumber;
    private String customerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Payment URL for redirection
    private String paymentUrl;
}