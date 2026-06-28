package com.ecommerce.inventory.dto.mapper.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ecommerce.inventory.dto.mapper.InventoryMapper;
import com.ecommerce.inventory.dto.request.InventoryRequest;
import com.ecommerce.inventory.dto.response.InventoryResponse;
import com.ecommerce.inventory.entity.Inventory;

@Component
public class InventoryMapperImpl implements InventoryMapper {

    @Override
    public Inventory toInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = new Inventory();
        inventory.setProductId(inventoryRequest.getProductId());
        inventory.setQuantityAvailable(inventoryRequest.getQuantityAvailable());
        inventory.setQuantityReserved(inventoryRequest.getQuantityReserved());
        inventory.setWarehouseLocation(inventoryRequest.getWarehouseLocation());
        inventory.setReorderThreshold(inventoryRequest.getReorderThreshold());
        inventory.setLastRestockedAt(inventoryRequest.getLastRestockedAt());
        return inventory;
    }

    @Override
    public InventoryResponse toInventoryResponse(Inventory inventory) {
        InventoryResponse response = new InventoryResponse();
        response.setProductId(inventory.getProductId());
        response.setQuantityAvailable(inventory.getQuantityAvailable());
        response.setQuantityReserved(inventory.getQuantityReserved());
        response.setWarehouseLocation(inventory.getWarehouseLocation());
        response.setReorderThreshold(inventory.getReorderThreshold());
        response.setLastRestockedAt(inventory.getLastRestockedAt());
        response.setQuantitySold(inventory.getQuantitySold());
        return response;
    }

    @Override
    public List<InventoryResponse> toInventoryResponse(List<Inventory> inventories) {
        return inventories.stream()
                .map(this::toInventoryResponse)
                .toList();
    }

    @Override
    public Inventory toUpdatedInventory(InventoryRequest inventoryRequest, Inventory existingInventory) {
        existingInventory.setQuantityAvailable(inventoryRequest.getQuantityAvailable());
        existingInventory.setQuantityReserved(inventoryRequest.getQuantityReserved());
        if (inventoryRequest.getWarehouseLocation() != null) {
            existingInventory.setWarehouseLocation(inventoryRequest.getWarehouseLocation());
        }
        if(inventoryRequest.getReorderThreshold() != null) {
            existingInventory.setReorderThreshold(inventoryRequest.getReorderThreshold());
        }
        if(inventoryRequest.getLastRestockedAt() != null) {
            existingInventory.setLastRestockedAt(inventoryRequest.getLastRestockedAt());
        }
        return existingInventory;
    }
    // Implement the mapping methods here
    
}
