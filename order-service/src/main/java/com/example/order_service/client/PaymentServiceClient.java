package com.example.order_service.client;

import com.example.order_service.dto.request.PaymentRequestDto;
import com.example.order_service.dto.response.PaymentResponseDto;
import com.example.order_service.exception.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class PaymentServiceClient {

    private final RestTemplate restTemplate;
    private final String paymentServiceUrl;

    public PaymentServiceClient(RestTemplate restTemplate,
                                @Value("${services.payment.url:http://localhost:8086/payment}") String paymentServiceUrl) {
        this.restTemplate = restTemplate;
        this.paymentServiceUrl = paymentServiceUrl;
        System.out.println("PaymentServiceClient initialized with URL: {}"+ paymentServiceUrl);
    }
    public String getPaymentServiceUrl() {
        return this.paymentServiceUrl;
    }
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto) {
        try {
            System.out.println("Creating payment for order: {}, amount: {}"+ requestDto.getOrderId()+ requestDto.getAmount());
            String url = paymentServiceUrl + "/create";

            HttpEntity<PaymentRequestDto> requestEntity = new HttpEntity<>(requestDto);

            PaymentResponseDto response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    PaymentResponseDto.class
            ).getBody();

            if (response == null) {
                System.out.println("Failed to create payment. Null response received.");
                throw new ServiceUnavailableException("Failed to create payment");
            }

            System.out.println("Payment created successfully with URL: {}"+ response.getPaymentUrl());
            return response;

        } catch (Exception e) {
            System.out.println("Error creating payment for order: {}"+ requestDto.getOrderId()+ e);
            throw new ServiceUnavailableException("Could not connect to payment service: " + e.getMessage());
        }
    }
}