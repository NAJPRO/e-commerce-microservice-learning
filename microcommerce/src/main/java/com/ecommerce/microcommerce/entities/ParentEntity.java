package com.ecommerce.microcommerce.entities;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;


@MappedSuperclass
public class ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "active", nullable = false)
    private Boolean active = true;


    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;

    // Soft delete
    @Column(name = "deleted_at")
    private Instant deletedAt;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    // Getter And Setter
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getUpdatedAt(){
        return updatedAt;
    }

    public void setCreatedAt(Instant created){
        this.createdAt = created;
    }

    public void setUpdatedAt(Instant updated){
        this.updatedAt = updated;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

}
