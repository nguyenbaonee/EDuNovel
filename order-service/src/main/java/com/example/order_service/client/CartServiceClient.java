package com.example.order_service.client;

import com.example.order_service.dto.response.CartResponse;
import com.example.order_service.exception.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class CartServiceClient {

    private final RestTemplate restTemplate;
    private final String cartServiceUrl;

    public CartServiceClient(RestTemplate restTemplate,
                             @Value("${services.cart.url:http://localhost:8082/carts/}") String cartServiceUrl) {
        this.restTemplate = restTemplate;
        this.cartServiceUrl = cartServiceUrl;
        System.out.println("CartServiceClient initialized with URL: " + cartServiceUrl);
    }

    public String getCartServiceUrl() {
        return this.cartServiceUrl;
    }

    public CartResponse getCartByUserId(String userId) {
        try {
            System.out.println("\n🛒 CART SERVICE - Fetching cart for user: " + userId);
            String url = cartServiceUrl + userId;
            System.out.println("Full request URL: " + url);

            // Direct response without wrapper
            ResponseEntity<CartResponse> responseEntity = restTemplate.getForEntity(
                    url,
                    CartResponse.class
            );

            CartResponse cart = responseEntity.getBody();

            if (cart == null) {
                System.out.println("❌ CART SERVICE - No cart found or empty response");
                throw new ServiceUnavailableException("Cart not found for user: " + userId);
            }

            System.out.println("✅ CART SERVICE - Got cart with " + cart.getItems().size() + " items");
            System.out.println("💰 Total price: " + cart.getTotalPrice());
            return cart;

        } catch (Exception e) {
            System.out.println("❌ CART SERVICE ERROR: " + e.getMessage());
            e.printStackTrace(System.out);
            throw new ServiceUnavailableException("Could not connect to cart service: " + e.getMessage());
        }
    }

    public void clearCart(String userId) {
        try {
            System.out.println("🧹 CART SERVICE - Clearing cart for user: " + userId);
            String url = cartServiceUrl + userId;
            System.out.println("DELETE URL: " + url);
            restTemplate.delete(url);
            System.out.println("✅ CART SERVICE - Cart cleared successfully for user: " + userId);
        } catch (Exception e) {
            System.out.println("❌ CART SERVICE ERROR clearing cart: " + e.getMessage());
            e.printStackTrace(System.out);
            throw new ServiceUnavailableException("Could not connect to cart service: " + e.getMessage());
        }
    }
}