package com.example.order_service.entity;

import com.example.order_service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId;

    private String orderCode;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String paymentTransactionId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    private String phoneNumber;

    private String customerName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Order(String id, String userId, String orderCode, BigDecimal totalAmount,
                 OrderStatus status, String paymentTransactionId, List<OrderItem> items, String phoneNumber, String customerName,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.orderCode = orderCode;
        this.totalAmount = totalAmount;
        this.status = status;
        this.paymentTransactionId = paymentTransactionId;
        this.phoneNumber = phoneNumber;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.items = items == null ? new ArrayList<>() : items;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void addItem(OrderItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        item.setOrder(this);
    }
}