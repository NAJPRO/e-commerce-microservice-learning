package com.ecommerce.microcommerce.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.microcommerce.dtos.request.ProductRequest;
import com.ecommerce.microcommerce.dtos.response.ProductResponse;
import com.ecommerce.microcommerce.payload.ApiResponse;
import com.ecommerce.microcommerce.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts(
            @RequestParam(defaultValue = "") String category,
            @RequestParam(required = false) String keyword) {
        List<ProductResponse> products = category.trim().length() > 1
                ? productService.getProductByCategory(category.toLowerCase().trim())
                : productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success(200, "Produits récupérés avec succès", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(200, "Produit récupéré avec succès", productService.getProductById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody @Valid ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Produit créé avec succès", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateProduct(
            @PathVariable String id,
            @RequestBody @Valid ProductRequest request) {
        productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.success(200, "Produit mis à jour avec succès", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(200, "Produit supprimé avec succès", null));
    }

    @GetMapping("/{id}/existing")
    public ResponseEntity<ApiResponse<Boolean>> existingById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(200, "", productService.existingById(id)));
    }
}