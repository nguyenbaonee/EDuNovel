package com.example.cart_service.service;

import com.example.cart_service.client.ProductFeignClient;
import com.example.cart_service.dto.request.AddItemRequest;
import com.example.cart_service.dto.response.CartItemResponse;
import com.example.cart_service.dto.response.CartResponse;
import com.example.cart_service.dto.response.ProductResponse;
import com.example.cart_service.entity.Cart;
import com.example.cart_service.entity.CartItem;
import com.example.cart_service.repository.CartItemRepository;
import com.example.cart_service.repository.CartRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductFeignClient productFeignClient; 

    /**
     * Lấy userId từ SecurityContext (được set bởi UserContextFilter)
     */
    private String getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            log.debug("Current userId from SecurityContext: {}", authentication.getName());
            return authentication.getName();
        }
        log.error("❌ No userId found in SecurityContext");
        throw new RuntimeException("User not authenticated");
    }


    /**
     * Lấy giỏ hàng của user hiện tại (từ SecurityContext)
     */
    @Transactional(readOnly = true)
    public CartResponse getCurrentUserCart() {
        String userId = getCurrentUserId();
        log.info("Getting cart for userId: {}", userId);
        
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(Cart.builder().userId(userId).items(new ArrayList<>()).build());

        return mapToCartResponse(cart);
    }

    
    @Transactional
    public CartResponse addItemToCart(AddItemRequest request) {
        String userId = getCurrentUserId();
        log.info("=== ADDING ITEM TO CART ===");
        log.info("UserId: {}", userId);
        log.info("ProductId: {}", request.getCourseId());
        log.info("Quantity: {}", request.getQuantity());

        // Fetch product information
        ProductResponse product = null;
        try {
            log.info("Calling productFeignClient.getProduct()");
            var response = productFeignClient.getProduct(request.getCourseId());
            
            if (response != null && response.getResult() != null) {
                product = response.getResult();
                log.info("Product fetched successfully: {}", product.getName());
            } else {
                log.error("Product response is null");
                throw new RuntimeException("Product not found");
            }

        } catch (FeignException.ServiceUnavailable e) {
            log.error("Product service unavailable: {}", e.getMessage());
            throw new RuntimeException("Product service unavailable: " + e.getMessage());
        } catch (FeignException e) {
            log.error("Error calling product service: {}", e.getMessage());
            throw new RuntimeException("Error calling product service: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            throw new RuntimeException("Unexpected error: " + e.getMessage());
        }

        if (product == null) {
            log.error("Product is null");
            throw new RuntimeException("Product not found");
        }

        if (!product.isAvailable()) {
            log.error("Product is not available");
            throw new RuntimeException("Product is not available");
        }

        // Get or create cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(Cart.builder().userId(userId).items(new ArrayList<>()).build());

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getCourseId().equals(request.getCourseId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update quantity
            CartItem item = existingItem.get();
            item.setPrice(product.getPrice());
        } else {
            // Create new cart item
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .courseId(request.getCourseId())
                    .price(product.getPrice())
                    .build();
            cart.getItems().add(newItem);
            log.info("Added new item to cart");
        }

        cart = cartRepository.save(cart);
        log.info("✅ Cart saved successfully");

        return mapToCartResponse(cart);
    }
 /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng của user hiện tại
     */
    @Transactional
    public CartResponse updateCartItem(String productId, Integer quantity) {
        String userId = getCurrentUserId();
        log.info("Updating cart item for userId: {}, productId: {}, quantity: {}", userId, productId, quantity);
        
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem itemToUpdate = cart.getItems().stream()
                .filter(item -> item.getCourseId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        if (quantity <= 0) {
            cart.getItems().remove(itemToUpdate);
            log.info("Removed item from cart");
        } else {
            log.info("Updated item quantity to {}", quantity);
        }

        cart = cartRepository.save(cart);
        return mapToCartResponse(cart);
    }
    
    /**
     * Xóa sản phẩm khỏi giỏ hàng của user hiện tại
     */
    @Transactional
    public void removeItemFromCart(String productId) {
        String userId = getCurrentUserId();
        log.info("Removing item from cart for userId: {}, productId: {}", userId, productId);
        
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        boolean removed = cart.getItems().removeIf(item -> item.getCourseId().equals(productId));
        
        if (removed) {
            cartRepository.save(cart);
            log.info("✅ Item removed from cart");
        } else {
            log.warn("Item not found in cart");
            throw new RuntimeException("Item not found in cart");
        }
    }

/**
     * Xóa toàn bộ giỏ hàng của user hiện tại
     */
    @Transactional
    public void clearCart() {
        String userId = getCurrentUserId();
        log.info("Clearing cart for userId: {}", userId);
        
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().clear();
        cartRepository.save(cart);
        log.info("✅ Cart cleared");
    }


    private CartResponse mapToCartResponse(Cart cart) {
        List<CartItemResponse> itemResponses = cart.getItems().stream()
                .map(this::mapToCartItemResponse)
                .collect(Collectors.toList());

        BigDecimal totalPrice = itemResponses.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .items(itemResponses)
                .totalPrice(totalPrice)
                .build();
    }

    private CartItemResponse mapToCartItemResponse(CartItem item) {
        ProductResponse product = null;
        try {
             var response = productFeignClient.getProduct(item.getCourseId());
            if (response != null && response.getResult() != null) {
                product = response.getResult();
            }
        } catch (Exception e) {
            log.error("Error fetching product for cart item: {}", e.getMessage());
            // Không throw exception, chỉ log lỗi
        }

        return CartItemResponse.builder()
                .id(item.getId())
                .courseId(item.getCourseId())
                .courseName(product != null ? product.getName() : "Unknown")
                .price(item.getPrice())
                .build();
    }
}