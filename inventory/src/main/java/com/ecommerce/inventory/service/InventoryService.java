package com.ecommerce.inventory.service;

import java.util.List;

import com.ecommerce.inventory.dto.request.InventoryRequest;
import com.ecommerce.inventory.dto.response.InventoryResponse;

public interface InventoryService {
    InventoryResponse createInventory(InventoryRequest inventoryRequest);
    InventoryResponse updateInventory(String productId, InventoryRequest inventoryRequest);
    InventoryResponse getInventoryByProductId(String productId);
    List<InventoryResponse> getAllInventories();
    void deleteInventory(String productId);

    Boolean isProductInStock(String productId);
    Boolean isProductInStock(String productId, Integer quantity);
    void reserveProduct(String productId, Integer quantity);
    void releaseProduct(String productId, Integer quantity);
    void confirmProductReservation(String productId, Integer quantity);
    void restockProduct(String productId, Integer quantity);
    Boolean isPossibleToDeleteProduct(String productId);
}
