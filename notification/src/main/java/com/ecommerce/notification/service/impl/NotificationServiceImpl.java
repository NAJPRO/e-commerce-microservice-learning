package com.ecommerce.notification.service.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ecommerce.notification.dto.event.OrderCreatedEvent;
import com.ecommerce.notification.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
class NotificationServiceImpl implements NotificationService {

    @KafkaListener(topics = "order-created", groupId = "notification-service")
    @Override
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info(event.toString());
        log.info("📩 Notification : commande reçue → " + event.orderId()
                + " pour " + event.customerName()
                + " (" + event.totalAmount() + " €)");
        // Ici, plus tard : envoi d'un email, log en base, e
    }

}