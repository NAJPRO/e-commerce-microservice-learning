package com.ecommerce.inventory.dto.mapper;

import java.util.List;

import com.ecommerce.inventory.dto.request.InventoryRequest;
import com.ecommerce.inventory.dto.response.InventoryResponse;
import com.ecommerce.inventory.entity.Inventory;

public interface InventoryMapper {
    Inventory toInventory(InventoryRequest inventoryRequest);
    Inventory toUpdatedInventory(InventoryRequest inventoryRequest, Inventory existingInventory);
    InventoryResponse toInventoryResponse(Inventory inventory);
    List<InventoryResponse> toInventoryResponse(List<Inventory> inventories);
}
