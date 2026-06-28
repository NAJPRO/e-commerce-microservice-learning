package com.orders.orderservice.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order extends ParentEntity {
    @Column(name = "customer_name")
    private String customerName;
    private String status; //  (Enum)PENDING, CONFIRMED, REJECTED_NO_STOCK, REJECTED_PRODUCT_NOT_FOUND, CANCELLED
    @Column(name = "tatal_amount")
    private BigDecimal totalAmount;
    @Column(name = "rejection_reason", nullable = true)
    private String rejectionReason; //  (Enum)NO_STOCK, PRODUCT_NOT_FOUND, CANCELLED,
    @Column(name = "confirmed_at", nullable = true)
    private Instant confirmedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItems;
}
