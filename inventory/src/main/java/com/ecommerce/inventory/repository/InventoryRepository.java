package com.ecommerce.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ecommerce.inventory.entity.Inventory;

public interface InventoryRepository extends CrudRepository<Inventory, String> {
    @Query("SELECT i FROM Inventory i WHERE i.productId = :productId")
    Optional<Inventory> findByProductId(
            @Param("productId") String productId);
}
