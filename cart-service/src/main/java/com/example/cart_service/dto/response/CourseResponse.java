package com.example.cart_service.dto.response;

import com.example.cart_service.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    Long id;

    String name;

    String code;

    String description;
    Status status;

    String author;

    BigDecimal price;
    String descriptionDetail;
}
