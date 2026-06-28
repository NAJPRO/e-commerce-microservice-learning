package com.ecommerce.inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.inventory.dto.request.InventoryRequest;
import com.ecommerce.inventory.dto.request.ReserveStockRequest;
import com.ecommerce.inventory.dto.response.InventoryResponse;
import com.ecommerce.inventory.payload.ApiResponse;
import com.ecommerce.inventory.service.InventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<InventoryResponse>> createInventory(@RequestBody @Valid InventoryRequest request) {
        InventoryResponse response = inventoryService.createInventory(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Inventaire créé avec succès", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getAllInventories() {
        return ResponseEntity.ok(ApiResponse.success(200, "Inventaires récupérés avec succès", inventoryService.getAllInventories()));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<InventoryResponse>> getInventoryByProductId(@PathVariable String productId) {
        return ResponseEntity.ok(ApiResponse.success(200, "Inventaire récupéré avec succès", inventoryService.getInventoryByProductId(productId)));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<InventoryResponse>> updateInventory(
            @PathVariable String productId,
            @RequestBody InventoryRequest request) {
        InventoryResponse response = inventoryService.updateInventory(productId, request);
        return ResponseEntity.ok(ApiResponse.success(200, "Inventaire mis à jour avec succès", response));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteInventory(@PathVariable String productId) {
        inventoryService.deleteInventory(productId);
        return ResponseEntity.ok(ApiResponse.success(200, "Inventaire supprimé avec succès", null));
    }

    @GetMapping("/{productId}/stock")
    public ResponseEntity<ApiResponse<Boolean>> isProductInStock(@PathVariable String productId) {
        return ResponseEntity.ok(ApiResponse.success(200, "", inventoryService.isProductInStock(productId)));
    }

    @GetMapping("/{productId}/stock/{quantity}")
    public ResponseEntity<ApiResponse<Boolean>> isProductInStock(
            @PathVariable String productId,
            @PathVariable Integer quantity) {
        return ResponseEntity.ok(ApiResponse.success(200, "", inventoryService.isProductInStock(productId, quantity)));
    }

    @GetMapping("/{productId}/isPossibleToDelete")
    public ResponseEntity<ApiResponse<Boolean>> isPossibleToDelete(@PathVariable String productId) {
        return ResponseEntity.ok(ApiResponse.success(200, "", inventoryService.isPossibleToDeleteProduct(productId)));
    }

    @PostMapping("/{productId}/reserve")
    public ResponseEntity<ApiResponse<Void>> reserveProduct(
            @PathVariable String productId,
            @RequestBody @Valid ReserveStockRequest dto) {
        inventoryService.reserveProduct(productId, dto.getQuantity());
        return ResponseEntity.ok(ApiResponse.success(200, "Stock réservé avec succès", null));
    }

    @PostMapping("/{productId}/release")
    public ResponseEntity<ApiResponse<Void>> releaseProduct(
            @PathVariable String productId,
            @RequestBody @Valid ReserveStockRequest dto) {
        inventoryService.releaseProduct(productId, dto.getQuantity());
        return ResponseEntity.ok(ApiResponse.success(200, "Réservation libérée avec succès", null));
    }

    @PostMapping("/{productId}/confirm")
    public ResponseEntity<ApiResponse<Void>> confirmProductReservation(
            @PathVariable String productId,
            @RequestBody @Valid ReserveStockRequest dto) {
        inventoryService.confirmProductReservation(productId, dto.getQuantity());
        return ResponseEntity.ok(ApiResponse.success(200, "Réservation confirmée avec succès", null));
    }

    @PostMapping("/{productId}/restock")
    public ResponseEntity<ApiResponse<Void>> restockProduct(
            @PathVariable String productId,
            @RequestBody @Valid ReserveStockRequest dto) {
        inventoryService.restockProduct(productId, dto.getQuantity());
        return ResponseEntity.ok(ApiResponse.success(200, "Réapprovisionnement effectué avec succès", null));
    }
}