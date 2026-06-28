package com.ecommerce.microcommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.microcommerce.entities.Category;
import com.ecommerce.microcommerce.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
    List<Product> findAll();
    List<Product> findByCategory(Category category);

    @Query("SELECT p FROM Product p WHERE p.name LIKE :keyword")
    List<Product> searchByName(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.sku IN :skus")
    List<Product> findAllBySkus(List<String> skus);

    Boolean existsBySku(String sku);
}
