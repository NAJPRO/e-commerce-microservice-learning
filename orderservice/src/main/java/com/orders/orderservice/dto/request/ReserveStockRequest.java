package com.orders.orderservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveStockRequest {
    @NotNull
    private Integer quantity;
    // getter/setter (ou record si tu préfères)
}