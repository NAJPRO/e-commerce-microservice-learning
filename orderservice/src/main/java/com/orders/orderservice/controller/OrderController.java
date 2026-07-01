package com.orders.orderservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orders.orderservice.dto.request.OrderRequest;
import com.orders.orderservice.dto.response.OrderItemResponse;
import com.orders.orderservice.dto.response.OrderResponse;
import com.orders.orderservice.payload.ApiResponse;
import com.orders.orderservice.payload.PageResponse;
import com.orders.orderservice.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody @Valid OrderRequest entity) {
        System.out.println("Received OrderRequest: " + entity.toString());
        OrderResponse response = service.createOrder(entity);
        return ResponseEntity.ok(ApiResponse.success(200, "Commande créée avec succès", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OrderResponse> orders = service.getAllOrders(page, size);
        return ResponseEntity.ok(ApiResponse.success(200, "Commandes récupérées avec succès", PageResponse.of(orders)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable String orderId) {
        OrderResponse response = service.getOrderById(orderId);
        return ResponseEntity.ok(ApiResponse.success(200, "Commande récupérée avec succès", response));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> getOrdersByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OrderResponse> orders = service.getOrderByStatus(status, page, size);
        return ResponseEntity.ok(ApiResponse.success(200, "Commandes filtrées par statut", PageResponse.of(orders)));
    }

    @GetMapping("/customer/{customerName}")
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> getOrdersByCustomer(
            @PathVariable String customerName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OrderResponse> orders = service.getOrdersByCustomer(customerName, page, size);
        return ResponseEntity.ok(ApiResponse.success(200, "Commandes filtrées par client", PageResponse.of(orders)));
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<ApiResponse<List<OrderItemResponse>>> getOrderItems(@PathVariable String orderId) {
        List<OrderItemResponse> items = service.getOrderItems(orderId);
        return ResponseEntity.ok(ApiResponse.success(200, "Lignes de commande récupérées avec succès", items));
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(@PathVariable String orderId) {
        service.cancelOrder(orderId);
        return ResponseEntity.ok(ApiResponse.success(200, "Commande annulée avec succès", null));
    }
}
