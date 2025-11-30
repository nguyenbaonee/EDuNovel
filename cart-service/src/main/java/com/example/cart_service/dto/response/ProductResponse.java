package com.example.cart_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private String brand;
    private String category;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private boolean available;
}