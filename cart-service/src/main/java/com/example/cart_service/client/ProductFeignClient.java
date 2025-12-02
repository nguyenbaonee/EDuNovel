package com.example.cart_service.client;

import com.example.cart_service.dto.request.ApiResponse;
import com.example.cart_service.dto.response.CourseResponse;
import com.example.cart_service.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url= "${app.services.product}")
public interface ProductFeignClient {
    
    @GetMapping("/api/courses/{courseId}")
    ApiResponse<CourseResponse> getProduct(@PathVariable("courseId") Long courseId);
}