package com.orders.orderservice.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.orders.orderservice.dto.event.OrderCreatedEvent;
import com.orders.orderservice.service.OrderEventProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventProducerImpl implements OrderEventProducer {
       private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
       private static final String TOPIC = "order-created";

    @Override
    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
         log.info("Envoi Kafka...");
        // Implementation to publish the OrderCreatedEvent to Kafka
        // You can use KafkaTemplate or any other Kafka client library to send the event
        kafkaTemplate.send(TOPIC, event.orderId(), event);
    }

}
