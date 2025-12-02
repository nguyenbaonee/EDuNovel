package com.example.cart_service.controller;

import com.example.cart_service.dto.request.AddItemRequest;
import com.example.cart_service.dto.request.ApiResponse;
import com.example.cart_service.dto.response.CartResponse;
import com.example.cart_service.service.CartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    
    private final CartService cartService;

    /**
     * Lấy giỏ hàng của user hiện tại
     */
    @GetMapping("by-user/{userId}")
    public ResponseEntity<ApiResponse<CartResponse>> getCurrentUserCart(HttpServletRequest httpRequest,
                                                                        @PathVariable String userId) {
    
    try {
        CartResponse cart = cartService.getCurrentUserCart(userId);
        
        return ResponseEntity.ok(ApiResponse.<CartResponse>builder()
                .code(1000)
                .message("Get cart successfully")
                .result(cart)
                .build());
                
    } catch (Exception e) {
        log.error("❌ Error getting cart", e);
        return ResponseEntity.badRequest().body(ApiResponse.<CartResponse>builder()
                .code(1001)
                .message("Error: " + e.getMessage())
                .build());
    }
}

    /**
     * Thêm sản phẩm vào giỏ hàng của user hiện tại
     */
    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartResponse>> addItemToCart(
            @RequestBody @Valid AddItemRequest request) {
        
        try {
            CartResponse cart = cartService.addItemToCart(request);
            
            return ResponseEntity.ok(ApiResponse.<CartResponse>builder()
                    .code(1000)
                    .message("Item added to cart successfully")
                    .result(cart)
                    .build());
                    
        } catch (Exception e) {
            log.error("Error adding item to cart", e);
            return ResponseEntity.badRequest().body(ApiResponse.<CartResponse>builder()
                    .code(1001)
                    .message("Error: " + e.getMessage())
                    .build());
        }
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng
     */
    @PutMapping("/items/{productId}")
    public ResponseEntity<ApiResponse<CartResponse>> updateCartItem(
            @PathVariable String productId,
            @RequestBody Integer quantity) {
        
        log.info("=== UPDATE CART ITEM ===");
        log.info("ProductId: {}, Quantity: {}", productId, quantity);
        
        try {
            CartResponse cart = cartService.updateCartItem(productId, quantity);
            
            return ResponseEntity.ok(ApiResponse.<CartResponse>builder()
                    .code(1000)
                    .message("Cart item updated successfully")
                    .result(cart)
                    .build());
                    
        } catch (Exception e) {
            log.error("Error updating cart item", e);
            return ResponseEntity.badRequest().body(ApiResponse.<CartResponse>builder()
                    .code(1001)
                    .message("Error: " + e.getMessage())
                    .build());
        }
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<ApiResponse<String>> removeItemFromCart(
            @PathVariable String userId,
            @PathVariable Long productId) {
        
        log.info("=== REMOVE ITEM FROM CART ===");
        log.info("ProductId: {}", productId);
        
        try {
            cartService.removeItemFromCart(userId, productId);
            
            return ResponseEntity.ok(ApiResponse.<String>builder()
                    .code(1000)
                    .message("Item removed from cart successfully")
                    .result("Success")
                    .build());
                    
        } catch (Exception e) {
            log.error("Error removing item from cart", e);
            return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                    .code(1001)
                    .message("Error: " + e.getMessage())
                    .build());
        }
    }

    /**
     * Xóa toàn bộ giỏ hàng
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> clearCart(@PathVariable String userId) {
        log.info("=== CLEAR CART ===");
        
        try {
            cartService.clearCart(userId);
            
            return ResponseEntity.ok(ApiResponse.<String>builder()
                    .code(1000)
                    .message("Cart cleared successfully")
                    .result("Success")
                    .build());
                    
        } catch (Exception e) {
            log.error("Error clearing cart", e);
            return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                    .code(1001)
                    .message("Error: " + e.getMessage())
                    .build());
        }
    }
}