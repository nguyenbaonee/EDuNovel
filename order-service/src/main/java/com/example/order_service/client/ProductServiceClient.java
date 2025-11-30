package com.example.order_service.client;

import com.example.order_service.dto.request.ApiResponse;
import com.example.order_service.dto.request.StockUpdateRequestDto;
import com.example.order_service.dto.response.ProductResponse;
import com.example.order_service.exception.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ProductServiceClient {

    private final RestTemplate restTemplate;
    private final String productServiceUrl;

    public ProductServiceClient(RestTemplate restTemplate,
                                @Value("${services.product.url:http://localhost:8088}") String productServiceUrl) {
        this.restTemplate = restTemplate;
        this.productServiceUrl = productServiceUrl;
        System.out.println("ProductServiceClient initialized with URL: {}"+ productServiceUrl);
    }
    public String getProductServiceUrl() {
        return this.productServiceUrl;
    }
    public ProductResponse getProductById(Long productId) {
        try {
            System.out.println("Fetching product details for ID: {}"+ productId);
            String url = productServiceUrl + "/products/" + productId;

            ApiResponse<ProductResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<ProductResponse>>() {}
            ).getBody();

            if (response == null || response.getResult() == null) {
                System.out.println("Product not found with ID: {}"+ productId);
                throw new ServiceUnavailableException("Product not found: " + productId);
            }

            System.out.println("Product fetched successfully: {}"+ response.getResult().getName());
            return response.getResult();

        } catch (Exception e) {
            System.out.println("Error fetching product with ID: {}"+ productId+ e);
            throw new ServiceUnavailableException("Could not connect to product service: " + e.getMessage());
        }
    }

    public void reduceProductStock(Long productId) {
        try {
            System.out.println("Reducing stock for product: {}, quantity: {}"+ productId+ 1);
            String url = productServiceUrl + "/products/stock";

            StockUpdateRequestDto request = StockUpdateRequestDto.builder()
                    .courseId(productId)
                    .quantity(1)
                    .operation("REDUCE") // Giảm số lượng trong kho
                    .build();

            HttpEntity<StockUpdateRequestDto> requestEntity = new HttpEntity<>(request);

            restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<ApiResponse<Void>>() {}
            );

            System.out.println("Stock reduced successfully for product: {}"+ productId);

        } catch (Exception e) {
            System.out.println("Error reducing stock for product: {}"+ productId+ e);
            throw new ServiceUnavailableException("Could not connect to product service: " + e.getMessage());
        }
    }
}