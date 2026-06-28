package com.ecommerce.inventory.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.inventory.dto.response.ProductDto;
import com.ecommerce.inventory.payload.ApiResponse;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClient {
    @GetMapping("/{productId}")
    ApiResponse<ProductDto> getProductById(@PathVariable("productId") String productId);

}
