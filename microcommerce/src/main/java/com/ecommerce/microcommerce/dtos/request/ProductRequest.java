package com.ecommerce.microcommerce.dtos.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Product price is required")
    @Min(value = 0, message = "Product price must be a positive number")
    private BigDecimal price;
    @NotBlank
    private String sku;
    private String description;
    @NotBlank
    private String categoryId;

    public ProductRequest() {
    }
    
    public String getSku(){
        return sku;
    }
    public void setSku(String sku){
        this.sku = sku;
    }
    public String getCategory(){
        return categoryId;
    }
    public void setCategory(String cat){
        this.categoryId = cat;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String des){
        this.description = des;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
