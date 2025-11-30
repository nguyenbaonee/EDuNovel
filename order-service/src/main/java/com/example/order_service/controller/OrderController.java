package com.example.order_service.controller;

import com.example.order_service.dto.request.ApiResponse;
import com.example.order_service.dto.request.OrderRequest;
import com.example.order_service.dto.response.OrderResponse;
import com.example.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody @Valid OrderRequest request) {
        log.info("Received request to create order for user: {}", request.getUserId());
        OrderResponse orderResponse = orderService.createOrder(request);

        log.info("Order created successfully: {}", orderResponse.getId());
        return ResponseEntity.ok(ApiResponse.<OrderResponse>builder()
                .code(0)
                .message("Order created successfully")
                .result(orderResponse)
                .build());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable String orderId) {
        log.info("Received request to get order with ID: {}", orderId);
        OrderResponse orderResponse = orderService.getOrderById(orderId);

        return ResponseEntity.ok(ApiResponse.<OrderResponse>builder()
                .code(0)
                .result(orderResponse)
                .build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByUserId(@PathVariable String userId) {
        log.info("Received request to get orders for user: {}", userId);
        List<OrderResponse> orders = orderService.getOrdersByUserId(userId);

        return ResponseEntity.ok(ApiResponse.<List<OrderResponse>>builder()
                .code(0)
                .result(orders)
                .build());
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        log.info("Received request to get all orders (Admin)");
        List<OrderResponse> orders = orderService.getAllOrders();

        return ResponseEntity.ok(ApiResponse.<List<OrderResponse>>builder()
                .code(0)
                .message("All orders retrieved successfully")
                .result(orders)
                .build());
    }
    @PostMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam String status,
            @RequestParam(required = false) String transactionId) {

        log.info("Received request to update order status: {}, status: {}, transactionId: {}",
                orderId, status, transactionId);

        OrderResponse updatedOrder = orderService.updateOrderStatus(orderId, status, transactionId);

        return ResponseEntity.ok(ApiResponse.<OrderResponse>builder()
                .code(0)
                .message("Order status updated successfully")
                .result(updatedOrder)
                .build());
    }
    @PostMapping("/{orderId}/status123")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatusLegacy(
            @PathVariable String orderId,
            @RequestParam String status,
            @RequestParam(required = false) String transactionId) {

        log.info("Received request to update order status: {}, status: {}, transactionId: {}",
                orderId, status, transactionId);

        OrderResponse updatedOrder = orderService.updateOrderStatus(orderId, status, transactionId);

        return ResponseEntity.ok(ApiResponse.<OrderResponse>builder()
                .code(0)
                .message("Order status updated successfully")
                .result(updatedOrder)
                .build());
    }
}