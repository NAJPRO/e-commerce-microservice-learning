package com.ecommerce.microcommerce.services;

import java.util.List;

import com.ecommerce.microcommerce.dtos.request.ProductRequest;
import com.ecommerce.microcommerce.dtos.response.ProductResponse;

public interface ProductService {
    ProductResponse getProductById(String id);
    List<ProductResponse> getProductByCategory(String category);
    List<ProductResponse> getAllProducts(); // Amélioration pour après avec page et size puis retourner une PageResponse
    ProductResponse createProduct(ProductRequest productRequest);
    void updateProduct(String id, ProductRequest productRequest);
    void deleteProduct(String id);

    void toogleProduct(String productId);
    Boolean existingById(String productId);
    List<ProductResponse> searchByName(String keyword);
    List<ProductResponse> getProductsBySkuList(List<String> skus);
}
