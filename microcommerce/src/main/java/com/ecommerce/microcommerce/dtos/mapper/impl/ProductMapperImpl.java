package com.ecommerce.microcommerce.dtos.mapper.impl;

import org.springframework.stereotype.Component;

import com.ecommerce.microcommerce.dtos.mapper.ProductMapper;
import com.ecommerce.microcommerce.dtos.request.ProductRequest;
import com.ecommerce.microcommerce.dtos.response.ProductResponse;
import com.ecommerce.microcommerce.entities.Product;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductResponse toProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setSku(product.getSku());
        productResponse.setCategoryId(product.getCategory().getId());
        productResponse.setDescription(product.getDescription());
        return productResponse;
    }

    @Override
    public Product toProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setSku(productRequest.getSku());
        return product;
    }

}
