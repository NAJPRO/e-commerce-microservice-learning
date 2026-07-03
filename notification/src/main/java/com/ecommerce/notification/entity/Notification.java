package com.ecommerce.notification.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "notifications")
public class Notification extends ParentEntity {
    private String orderId;
    private String customerName;
    private String message;
    private BigDecimal totalAmount;
    private String status;
    private Instant receivedAt;
    private Instant readAt;
}
