package com.ecommerce.microcommerce.dtos.mapper;


import com.ecommerce.microcommerce.dtos.request.ProductRequest;
import com.ecommerce.microcommerce.dtos.response.ProductResponse;
import com.ecommerce.microcommerce.entities.Product;

public interface ProductMapper {
    ProductResponse toProductResponse(Product product);
    Product toProduct(ProductRequest productRequest);
}
