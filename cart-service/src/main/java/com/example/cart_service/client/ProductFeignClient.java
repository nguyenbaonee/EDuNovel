package com.example.cart_service.client;

import com.example.cart_service.dto.request.ApiResponse;
import com.example.cart_service.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", path = "/products")
public interface ProductFeignClient {
    
    @GetMapping("/{courseId}")
    ApiResponse<ProductResponse> getProduct(@PathVariable("courseId") Long courseId);
}