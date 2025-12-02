package com.example.cart_service;

import com.example.cart_service.dto.response.CourseResponse;
import com.example.cart_service.dto.response.ProductResponse;

public class ProductResponseMapper {

    public static ProductResponse toProductResponse(CourseResponse courseResponse) {
        ProductResponse productResponse = new ProductResponse();

        // Ánh xạ từ CourseResponse sang ProductResponse
        productResponse.setId(String.valueOf(courseResponse.getId())); // convert Long to String
        productResponse.setName(courseResponse.getName());
        productResponse.setCode(courseResponse.getCode());
        productResponse.setDescription(courseResponse.getDescription());

        productResponse.setStatus(courseResponse.getStatus().name()); // convert Status to String
        productResponse.setAuthor(courseResponse.getAuthor());
        productResponse.setPrice(courseResponse.getPrice());
        productResponse.setDescriptionDetail(courseResponse.getDescriptionDetail());
        productResponse.setAvailable(true); // Bạn có thể điều chỉnh trạng thái availability theo logic riêng

        return productResponse;
    }
}
