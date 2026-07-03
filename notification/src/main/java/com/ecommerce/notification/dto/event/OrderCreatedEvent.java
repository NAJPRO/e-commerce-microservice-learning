package com.ecommerce.notification.dto.event;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderCreatedEvent(
        String orderId,
        String customerName,
        BigDecimal totalAmount,
        Instant createdAt
) {}