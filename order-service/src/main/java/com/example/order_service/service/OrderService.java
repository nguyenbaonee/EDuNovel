package com.example.order_service.service;

import com.example.order_service.client.CartServiceClient;
import com.example.order_service.client.PaymentServiceClient;
import com.example.order_service.client.ProductServiceClient;
import com.example.order_service.dto.request.OrderRequest;
import com.example.order_service.dto.request.PaymentRequestDto;
import com.example.order_service.dto.response.*;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderItem;
import com.example.order_service.enums.OrderStatus;
import com.example.order_service.exception.ResourceNotFoundException;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartServiceClient cartServiceClient;
    private final ProductServiceClient productServiceClient;
    private final PaymentServiceClient paymentServiceClient;


    public OrderResponse createOrder(OrderRequest orderRequest) {
        String userId = orderRequest.getUserId();
        log.info("Creating order for user: {}", userId);
        System.out.println("\n=============== START ORDER CREATION ===============");
        System.out.println("👤 Creating order for user: " + userId);

        try {
            // 1. Lấy giỏ hàng từ Cart Service
            System.out.println("\n🛒 CALLING CART SERVICE...");
            System.out.println("URL: " + cartServiceClient.getCartServiceUrl() + userId);
            CartResponse cart = cartServiceClient.getCartByUserId(userId);
            System.out.println("✅ Cart fetched successfully - Items: " + (cart.getItems() != null ? cart.getItems().size() : 0));
            System.out.println("💰 Total price: " + cart.getTotalPrice());

            if (cart.getItems() == null || cart.getItems().isEmpty()) {
                System.out.println("❌ ERROR: Cart is empty!");
                log.error("Cart is empty for user: {}", userId);
                throw new IllegalStateException("Cart is empty, cannot create an order");
            }

            // 2. Kiểm tra sản phẩm trong giỏ hàng có tồn tại và có sẵn không
            System.out.println("\n🔍 VALIDATING PRODUCTS IN CART...");
            for (CartItemResponse cartItem : cart.getItems()) {
                System.out.println("\n📦 CALLING PRODUCT SERVICE...");
                System.out.println("URL: " + productServiceClient.getProductServiceUrl() + "/products/" + cartItem.getProductId());
                ProductResponse product = productServiceClient.getProductById(cartItem.getProductId());
                System.out.println("Product: " + product.getName() + " | Available: " + product.isAvailable() + " | Stock: " + product.getStockQuantity());

                if (!product.isAvailable()) {
                    System.out.println("❌ ERROR: Product is not available: " + product.getName());
                    log.error("Product is not available: {}", product.getName());
                    throw new IllegalStateException("Product is not available: " + product.getName());
                }

                if (product.getStockQuantity() < cartItem.getQuantity()) {
                    System.out.println("❌ ERROR: Insufficient stock for product: " + product.getName());
                    System.out.println("Available: " + product.getStockQuantity() + ", Requested: " + cartItem.getQuantity());
                    log.error("Insufficient stock for product: {}. Available: {}, Requested: {}",
                            product.getName(), product.getStockQuantity(), cartItem.getQuantity());
                    throw new IllegalStateException("Insufficient stock for product: " + product.getName() +
                            ". Available: " + product.getStockQuantity() + ", Requested: " + cartItem.getQuantity());
                }
            }

            // 3. Tạo đơn hàng mới
            System.out.println("\n📝 CREATING NEW ORDER...");
            String orderCode = generateOrderCode();
            System.out.println("Generated Order Code: " + orderCode);

            Order order = Order.builder()
                    .userId(userId)
                    .orderCode(orderCode)
                    .totalAmount(cart.getTotalPrice())
                    .status(OrderStatus.PENDING)
                    .phoneNumber(orderRequest.getPhoneNumber())
                    .customerName(orderRequest.getCustomerName())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            // 4. Thêm các sản phẩm vào đơn hàng
            System.out.println("\n📦 ADDING ITEMS TO ORDER...");
            for (CartItemResponse cartItem : cart.getItems()) {
                OrderItem orderItem = OrderItem.builder()
                        .courseId(cartItem.getProductId())
                        .courseName(cartItem.getProductName())
                        .courseImage(cartItem.getProductImage())
                        .price(cartItem.getPrice())
                        .build();

                order.addItem(orderItem);
                System.out.println("Added: " + cartItem.getProductName() + " x" + cartItem.getQuantity() + " - $" + cartItem.getSubtotal());
            }

            // 5. Lưu đơn hàng vào cơ sở dữ liệu
            System.out.println("\n💾 SAVING ORDER TO DATABASE...");
            order = orderRepository.save(order);
            System.out.println("✅ Order saved with ID: " + order.getId());
            System.out.println("✅ Order Code: " + order.getOrderCode());
            log.info("Order created with ID: {}, Code: {}", order.getId(), order.getOrderCode());

            // 6. Cập nhật trạng thái đơn hàng sang AWAITING_PAYMENT
            System.out.println("\n🔄 UPDATING ORDER STATUS TO AWAITING_PAYMENT...");
            order.setStatus(OrderStatus.AWAITING_PAYMENT);
            order = orderRepository.save(order);
            System.out.println("✅ Order status updated successfully");

            // 7. Gọi Payment Service để tạo payment và lấy URL thanh toán
            System.out.println("\n💳 CALLING PAYMENT SERVICE...");
            PaymentRequestDto paymentRequest = PaymentRequestDto.builder()
                    .orderId(order.getId())
                    .orderCode(order.getOrderCode())
                    .amount(order.getTotalAmount())
                    .paymentMethod("VNPAY") // Mặc định là VNPAY
                    .userId(userId)
                    .returnUrl(orderRequest.getReturnUrl()) // Nếu có
                    .build();

            System.out.println("URL: " + paymentServiceClient.getPaymentServiceUrl() + "/create");
            System.out.println("Request: " + paymentRequest);
            PaymentResponseDto paymentResponse = paymentServiceClient.createPayment(paymentRequest);
            System.out.println("✅ Payment created successfully");
            System.out.println("🔗 PAYMENT URL: " + paymentResponse.getPaymentUrl());
            log.info("Payment created for order: {}, payment URL: {}", order.getId(), paymentResponse.getPaymentUrl());

            // 8. Tạo response bao gồm URL thanh toán
            System.out.println("\n🔄 PREPARING RESPONSE...");
            OrderResponse response = mapOrderToResponse(order);
            response.setPaymentUrl(paymentResponse.getPaymentUrl());


            return response;

        } catch (Exception e) {
            System.out.println("\n❌ ERROR DURING ORDER CREATION: " + e.getMessage());
            e.printStackTrace(System.out);
            System.out.println("=============== ORDER CREATION FAILED ===============\n");
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(String id) {
        log.info("Fetching order with ID: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found with ID: {}", id);
                    return new ResourceNotFoundException("Order not found with ID: " + id);
                });

        return mapOrderToResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUserId(String userId) {
        log.info("Fetching orders for user: {}", userId);
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::mapOrderToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateOrderStatus(String orderId, String status, String transactionId) {
        log.info("Updating order status: {}, status: {}, transactionId: {}", orderId, status, transactionId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found with ID: {}", orderId);
                    return new ResourceNotFoundException("Order not found with ID: " + orderId);
                });

        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            log.error("Invalid order status: {}", status);
            throw new IllegalArgumentException("Invalid order status: " + status);
        }

        // Cập nhật trạng thái đơn hàng
        order.setStatus(newStatus);

        if (transactionId != null) {
            order.setPaymentTransactionId(transactionId);
        }

        order = orderRepository.save(order);
        log.info("Order status updated to: {} for order: {}", newStatus, orderId);

        // Nếu đơn hàng đã thanh toán, gọi ProductService để trừ stock
        if (newStatus == OrderStatus.PAID) {
            log.info("Order is paid, reducing product stock for order: {}", orderId);
            reduceProductStock(order);
        }

        return mapOrderToResponse(order);
    }


    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        log.info("Fetching all orders for admin");
        List<Order> orders = orderRepository.findAllByOrderByCreatedAtDesc();
        return orders.stream()
                .map(this::mapOrderToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByStatus(String status) {
        log.info("Fetching orders by status: {}", status);

        OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Invalid order status: {}", status);
            throw new IllegalArgumentException("Invalid order status: " + status);
        }

        List<Order> orders = orderRepository.findByStatusOrderByCreatedAtDesc(orderStatus);
        return orders.stream()
                .map(this::mapOrderToResponse)
                .collect(Collectors.toList());
    }
    private void reduceProductStock(Order order) {
        for (OrderItem item : order.getItems()) {
            try {
                productServiceClient.reduceProductStock(item.getCourseId());
            } catch (Exception e) {
                log.error("Error reducing stock for product: {}", item.getCourseId(), e);
                // Không throw exception để tránh rollback transaction
                // Cần có cơ chế riêng để xử lý các trường hợp lỗi này
            }
        }
    }

    private OrderResponse mapOrderToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .courseId(item.getCourseId())
                        .courseName(item.getCourseName())
                        .courseImage(item.getCourseImage())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .userId(order.getUserId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .paymentTransactionId(order.getPaymentTransactionId())
                .items(itemResponses)
                .phoneNumber(order.getPhoneNumber())
                .customerName(order.getCustomerName())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    private String generateOrderCode() {
        // Format: ORD-YYMMDDHHmmss-XXXX (X: random)
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(java.time.format.DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        String random = String.valueOf(1000 + new java.util.Random().nextInt(9000)); // 4 digit random number
        return "ORD-" + timestamp + "-" + random;
    }
}