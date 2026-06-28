package com.orders.orderservice.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderResponse {
private String id;
    private String customerName;
    private String status; // PENDING, CONFIRMED, REJECTED_NO_STOCK, etc.
    private BigDecimal totalAmount;
    private String rejectionReason; // Optionnel : cause du rejet
    private Instant confirmedAt; // Optionnel : date de confirmation
    private Instant createdAt; // Optionnel : utile pour l'historique côté React
    
    // Remplacement des champs uniques par la liste des articles de la commande
    private List<OrderItemResponse> items;
}
