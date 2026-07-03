package com.orders.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.orders.orderservice.dto.request.ReserveStockRequest;
import com.orders.orderservice.payload.ApiResponse;

@FeignClient(name = "inventory-service", url = "${inventory.service.url}")
public interface InventoryClient {
    @GetMapping("/{productId}/stock/{quantity}")
    ApiResponse<Boolean> checkStock(@PathVariable("productId") String productId, @PathVariable("quantity") Integer quantity);
    @PostMapping("/{productId}/reserve")
    ApiResponse<Void> reserveProduct(@PathVariable("productId") String productId, @RequestBody ReserveStockRequest request);

    @PostMapping("/{productId}/confirm")
    ApiResponse<Void> confirmProductReservation(@PathVariable("productId") String productId, @RequestBody ReserveStockRequest request);

    @PostMapping("/{productId}/release")
    ApiResponse<Void> releaseProduct(@PathVariable("productId") String productId, @RequestBody ReserveStockRequest request);
}

