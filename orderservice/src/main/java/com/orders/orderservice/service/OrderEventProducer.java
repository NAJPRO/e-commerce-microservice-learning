package com.orders.orderservice.service;

import com.orders.orderservice.dto.event.OrderCreatedEvent;

public interface OrderEventProducer {
    void publishOrderCreatedEvent(OrderCreatedEvent event);
}
