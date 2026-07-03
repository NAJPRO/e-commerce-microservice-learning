package com.ecommerce.notification.service;

import com.ecommerce.notification.dto.event.OrderCreatedEvent;

public interface NotificationService {
    void handleOrderCreated(OrderCreatedEvent event);
}
