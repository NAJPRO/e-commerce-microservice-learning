package com.ecommerce.inventory.dto.request;

import java.time.Instant;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class    InventoryRequest {
    @NotBlank
    private String productId;
    @NotNull
    @Min(0)
    private Integer quantityAvailable;
    private Integer quantityReserved;
    @NotNull
    @Min(0)
    private Integer reorderThreshold;

    private String warehouseLocation;

    private Instant lastRestockedAt;

}
