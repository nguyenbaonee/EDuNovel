package com.example.order_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    // Optional payment method selection, default to VNPay if not specified
    @Builder.Default
    private String paymentMethod = "VNPAY";

    // Optional return URL for payment callback
    private String returnUrl;
}