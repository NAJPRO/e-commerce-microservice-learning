package com.orders.orderservice.dto.mapper.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.orders.orderservice.dto.mapper.OrderMapper;
import com.orders.orderservice.dto.request.OrderRequest;
import com.orders.orderservice.dto.response.OrderItemResponse;
import com.orders.orderservice.dto.response.OrderResponse;
import com.orders.orderservice.entity.Order;
import com.orders.orderservice.entity.OrderItem;

@Component
public class OrderMapperImpl implements OrderMapper {
    @Override
    public Order toEntity(OrderRequest request) {
        if (request == null) {
            return null;
        }

        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setStatus("PENDING"); // Statut initial par défaut à la création

        // Note : La conversion de OrderItemRequest vers OrderItem se fait généralement
        // ici ou dans le service.
        // Si tes OrderItemRequest contiennent productId et quantity, tu devras les
        // mapper et lier à l'entité 'order'.
        if (request.getItems() != null) {
            List<OrderItem> items = request.getItems().stream().map(itemRequest -> {
                OrderItem item = new OrderItem();
                item.setProductId(itemRequest.getProductId());
                item.setQuantity(itemRequest.getQuantity());
                item.setOrder(order); // Lien bidirectionnel important pour JPA
                return item;
            }).collect(Collectors.toList());

            order.setOrderItems(items);
        } else {
            order.setOrderItems(new ArrayList<>());
        }

        // Le totalAmount sera calculé plus tard dans ton OrderService après avoir
        // récupéré les prix du ProductService
        order.setTotalAmount(BigDecimal.ZERO);

        return order;
    }

    @Override
    public OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerName(order.getCustomerName());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setRejectionReason(order.getRejectionReason());
        response.setConfirmedAt(order.getConfirmedAt());
        response.setCreatedAt(order.getCreatedAt()); // Hérité de ParentEntity

        // Mapping propre de la liste des items de la commande
        response.setItems(this.toOrderItemResponse(order.getOrderItems()));

        return response;
    }

    @Override
    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        OrderItemResponse response = new OrderItemResponse();

        // Gestion du mapping de l'ID de la relation parente
        if (orderItem.getOrder() != null) {
            response.setOrderId(orderItem.getOrder().getId());
        }

        response.setProductId(orderItem.getProductId());
        response.setProductName(orderItem.getProductName());
        response.setUnitPrice(orderItem.getUnitPrice());
        response.setQuantity(orderItem.getQuantity());

        // Calcul automatique du sous-total pour l'affichage si le prix et la quantité
        // sont présents
        if (orderItem.getUnitPrice() != null && orderItem.getQuantity() != null) {
            response.setSubTotal(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        } else {
            response.setSubTotal(BigDecimal.ZERO);
        }

        return response;
    }

    @Override
    public List<OrderItemResponse> toOrderItemResponse(List<OrderItem> orderItems) {
        if (orderItems == null) {
            return Collections.emptyList();
        }

        return orderItems.stream()
                .map(this::toOrderItemResponse)
                .collect(Collectors.toList());
    }

}
