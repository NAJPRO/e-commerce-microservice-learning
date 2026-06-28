package com.orders.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.orders.orderservice.dto.response.ProductDto;
import com.orders.orderservice.payload.ApiResponse;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClient {
    @GetMapping("/{id}")
    ApiResponse<ProductDto> getProductById(@PathVariable("id") String id);

}
