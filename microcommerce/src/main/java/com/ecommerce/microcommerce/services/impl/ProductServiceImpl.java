package com.ecommerce.microcommerce.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.microcommerce.dtos.mapper.ProductMapper;
import com.ecommerce.microcommerce.dtos.request.ProductRequest;
import com.ecommerce.microcommerce.dtos.response.ProductResponse;
import com.ecommerce.microcommerce.entities.Product;
import com.ecommerce.microcommerce.exception.ProductExistBySkuException;
import com.ecommerce.microcommerce.repositories.ProductRepository;
import com.ecommerce.microcommerce.services.CategoryService;
import com.ecommerce.microcommerce.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductMapper mapper;
    private final ProductRepository repository;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository repo, ProductMapper map, CategoryService categoryService){
        this.mapper = map;
        this.repository = repo;
        this.categoryService = categoryService;
    }
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = mapper.toProduct(productRequest);
        if(repository.existsBySku(product.getSku())){
            throw new ProductExistBySkuException("Un produit existe déjà avec le sku " + product.getSku());
        }
        product.setCategory(categoryService.getCategory(productRequest.getCategoryId()));
        product = repository.save(product); 
        System.out.println("Product created with ID: " + product.getId());
        return mapper.toProductResponse(product);
    }

    @Override
    public void deleteProduct(String id) {
        repository.deleteById(id);        
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = repository.findAll();
        return products.stream().map(product -> mapper.toProductResponse(product)).toList();
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = repository.findById(id).orElse(null);
        return mapper.toProductResponse(product);
    }

    @Override
    public void updateProduct(String id, ProductRequest productRequest) {
        Product product = repository.findById(id).orElse(null);
        if(product != null){
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setCategory(categoryService.getCategory(productRequest.getCategoryId()));
            repository.save(product);
        }        
    }
    @Override
    public Boolean existingById(String productId) {
        return repository.existsById(productId);
    }
    @Override
    public List<ProductResponse> getProductByCategory(String category) {
        List<Product> products = repository.findByCategory(categoryService.getCategory(category));
        return products.stream()
                .map(product -> mapper.toProductResponse(product))
                .toList();
    }
    @Override
    public List<ProductResponse> getProductsBySkuList(List<String> skus) {
        List<Product> products = repository.findAllBySkus(skus);
        return products.stream()
                .map(product -> mapper.toProductResponse(product))
                .toList();
    }
    @Override
    public List<ProductResponse> searchByName(String keyword) {
        List<Product> products = repository.searchByName(keyword);
        return products.stream()
                .map(product -> mapper.toProductResponse(product))
                .toList();
    }
    @Override
    public void toogleProduct(String productId) {
        Product product = repository.findById(productId).orElse(null);
        product.setActive(!product.getActive());
    }

}
