package com.orders.orderservice.dto.request;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
    private String productId;
    private Integer quantity;
}
