package com.example.cart_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    String code;
    private String description;
    String thumbnail;
    String status;
    String author;
    BigDecimal price;
    String descriptionDetail;
    private boolean available;
}