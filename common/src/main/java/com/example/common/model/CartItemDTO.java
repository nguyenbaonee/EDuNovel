package com.example.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private String id;
    private String courseId;
    private String cartId;
    private Integer quantity;
    private String name;
    private Long originPrice;
    private Long discountPrice;
    private Integer discountPercent;
    private Long avatarId;
}
