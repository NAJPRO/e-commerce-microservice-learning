package com.ecommerce.notification.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {
    private String id;
    private String orderId;
    private String customerName;
    private String message;
    private BigDecimal totalAmount;
    private String status;
    private Instant receivedAt;
}