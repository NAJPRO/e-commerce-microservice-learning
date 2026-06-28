package com.orders.orderservice.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    @NotBlank
    private String customerName;
    @NotEmpty
    private List<OrderItemRequest> items;
}
