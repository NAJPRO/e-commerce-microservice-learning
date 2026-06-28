package com.ecommerce.inventory.dto.response;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryResponse {
    private String id;

    private String productId;

    private Integer quantityAvailable;

    private Integer quantityReserved;
    
    private Integer quantitySold;

    private Integer reorderThreshold;

    private String warehouseLocation;

    private Instant lastRestockedAt;
}
