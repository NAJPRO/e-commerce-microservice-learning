package com.ecommerce.inventory.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.inventory.client.ProductClient;
import com.ecommerce.inventory.dto.mapper.InventoryMapper;
import com.ecommerce.inventory.dto.request.InventoryRequest;
import com.ecommerce.inventory.dto.response.InventoryResponse;
import com.ecommerce.inventory.dto.response.ProductDto;
import com.ecommerce.inventory.entity.Inventory;
import com.ecommerce.inventory.exception.ProductNotFoundException;
import com.ecommerce.inventory.repository.InventoryRepository;
import com.ecommerce.inventory.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final ProductClient productClient;
    private final InventoryRepository repository;
    private final InventoryMapper mapper;


    @Override
    public void confirmProductReservation(String productId, Integer quantity) {
        // Confirm the reserved quantity of the product in the inventory
        Inventory inventory = getInventoryByProductIdOrThrow(productId);
        if (inventory.getQuantityReserved() < quantity) {
            throw new IllegalArgumentException(
                    "Cannot confirm more than reserved quantity for product with ID " + productId);
        }
        inventory.setQuantityReserved(inventory.getQuantityReserved() - quantity);
        repository.save(inventory);
    }

    @Override
    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {
        // Check if the product exists in the Product Service
        ProductDto productDto = getProductDto(inventoryRequest.getProductId());
        // Create a new inventory entry for the product
        Inventory inventory = mapper.toInventory(inventoryRequest);
        Inventory savedInventory = repository.save(inventory);
        return mapper.toInventoryResponse(savedInventory);
    }

    @Override
    public void deleteInventory(String productId) {
        Inventory inventory = getInventoryByProductIdOrThrow(productId);
        repository.delete(inventory);
    }

    @Override
    public List<InventoryResponse> getAllInventories() {
        List<Inventory> inventories = (List<Inventory>) repository.findAll();
        return mapper.toInventoryResponse(inventories);
    }

    @Override
    public InventoryResponse getInventoryByProductId(String productId) {
        Inventory inventory = getInventoryByProductIdOrThrow(productId);
        return mapper.toInventoryResponse(inventory);
    }

    @Override
    public Boolean isProductInStock(String productId) {
        Inventory inventory = getInventoryByProductIdOrThrow(productId);
        return inventory.getQuantityAvailable() > 0;
    }

    @Override
    public Boolean isProductInStock(String productId, Integer quantity) {
        Inventory inventory = getInventoryByProductIdOrThrow(productId);
        return inventory.getQuantityAvailable() >= quantity;
    }

    @Override
    public void releaseProduct(String productId, Integer quantity) {
        // Release the reserved quantity of the product in the inventory
        Inventory inventory = getInventoryByProductIdOrThrow(productId);
        if (inventory.getQuantityReserved() < quantity) {
            throw new IllegalArgumentException(
                    "Cannot release more than reserved quantity for product with ID " + productId);
        }
        inventory.setQuantityReserved(inventory.getQuantityReserved() - quantity);
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantity);
        repository.save(inventory);

    }

    @Override
    public void reserveProduct(String productId, Integer quantity) {
        // Decrease the quantity available in the inventory for the specified product
        // and increase the reserved quantity
        Inventory inventory = getInventoryByProductIdOrThrow(productId);
        if (inventory.getQuantityAvailable() < quantity) {
            throw new IllegalArgumentException("Not enough stock available for product with ID " + productId);
        }
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() - quantity);
        inventory.setQuantityReserved(inventory.getQuantityReserved() == null ? 0 : inventory.getQuantityReserved() + quantity);
        repository.save(inventory);

    }

    @Override
    public void restockProduct(String productId, Integer quantity) {
        // Restock the product by increasing the quantity in the inventory
        Inventory inventory = getInventoryByProductIdOrThrow(productId);
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantity);
        repository.save(inventory);
    }

    @Override
    public InventoryResponse updateInventory(String productId, InventoryRequest inventoryRequest) {
        // Fetch product details from the Product Service then update the inventory
        ProductDto productDto = getProductDto(productId);
        Inventory existingInventory = getInventoryByProductIdOrThrow(productId);
        Inventory updatedInventory = mapper.toUpdatedInventory(inventoryRequest, existingInventory);
        Inventory finalInventory = repository.save(updatedInventory);
        return mapper.toInventoryResponse(finalInventory);
    }

    @Override
    public Boolean isPossibleToDeleteProduct(String productId) {
        Inventory inventory = getInventoryByProductIdOrThrow(productId);
        // @SQLRestriction("quantity_available >= 0 AND quantity_reserved >= 0 AND
        // quantity_sold >= 0 AND reorder_threshold >= 0")

        return !(inventory.getQuantityAvailable() >= 0 && inventory.getQuantityReserved() >= 0
                && inventory.getQuantitySold() >= 0 && inventory.getReorderThreshold() >= 0);

    }

    private ProductDto getProductDto(String productId) {
        try {
            ProductDto productDto = productClient.getProductById(productId).data();
            return productDto;
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found.");
        }
    }

    private Inventory getInventoryByProductIdOrThrow(String productId) {
        return repository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Inventory 2 for product with ID " + productId + " not found."));
    }

}
