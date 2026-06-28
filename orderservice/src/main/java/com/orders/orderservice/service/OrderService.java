package com.orders.orderservice.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;

import com.orders.orderservice.dto.request.OrderRequest;
import com.orders.orderservice.dto.response.OrderItemResponse;
import com.orders.orderservice.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(String orderId);
    Page<OrderResponse> getAllOrders(int page, int size);
    Page<OrderResponse> getOrdersByCustomer(String customerName, int page, int size);
    Page<OrderResponse> getOrderByStatus(String status, int page, int size);
    Void cancelOrder(String orderId);
    BigDecimal calculateTotal(String orderId);
    List<OrderItemResponse> getOrderItems(String orderId);
}
