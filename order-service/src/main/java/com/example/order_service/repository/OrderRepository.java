package com.example.order_service.repository;

import com.example.order_service.entity.Order;
import com.example.order_service.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserId(String userId);
    List<Order> findByUserIdAndStatus(String userId, OrderStatus status);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findAllByOrderByCreatedAtDesc();
    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);
}