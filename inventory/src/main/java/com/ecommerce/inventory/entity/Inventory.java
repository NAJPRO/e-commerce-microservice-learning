package com.ecommerce.inventory.entity;

import java.time.Instant;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE inventory SET deleted = true, deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Inventory extends ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "quantity_available")
    private Integer quantityAvailable = 0;

    @Column(name = "quantity_reserved")
    private Integer quantityReserved = 0;

    @Column(name = "quantity_sold")
    private Integer quantitySold = 0;

    @Column(name = "reorder_threshold")
    private Integer reorderThreshold = 0;

    @Column(name = "warehouse_location")
    private String warehouseLocation;

    @Column(name = "last_restocked_at")
    private Instant lastRestockedAt;
}
