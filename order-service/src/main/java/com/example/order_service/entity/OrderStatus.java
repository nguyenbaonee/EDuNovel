package com.example.order_service.enums;

public enum OrderStatus {
    PENDING,         // Đơn hàng mới được tạo
    AWAITING_PAYMENT,// Đơn hàng chờ thanh toán
    PAID,            // Đã thanh toán, chờ xử lý
    PROCESSING,      // Đang xử lý
    SHIPPED,         // Đã gửi hàng
    DELIVERED,       // Đã giao hàng
    CANCELED,        // Đã hủy
    REFUNDED,        // Đã hoàn tiền
    PAYMENT_FAILED   // Thanh toán thất bại
}