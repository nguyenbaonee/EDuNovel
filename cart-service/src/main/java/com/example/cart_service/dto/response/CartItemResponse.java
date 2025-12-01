package com.example.cart_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private String id;
    private Long courseId;
    private String courseName;
    private BigDecimal price;
    private BigDecimal subtotal;
}