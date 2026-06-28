package com.orders.orderservice.dto.mapper;

import java.util.List;

import com.orders.orderservice.dto.request.OrderRequest;
import com.orders.orderservice.dto.response.OrderItemResponse;
import com.orders.orderservice.dto.response.OrderResponse;
import com.orders.orderservice.entity.Order;
import com.orders.orderservice.entity.OrderItem;

public interface OrderMapper {
    Order toEntity(OrderRequest order);
    OrderResponse toResponse(Order order);
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
    List<OrderItemResponse> toOrderItemResponse(List<OrderItem> orderItems);

}
