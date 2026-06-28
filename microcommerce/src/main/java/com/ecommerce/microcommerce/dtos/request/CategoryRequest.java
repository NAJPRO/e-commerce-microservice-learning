package com.ecommerce.microcommerce.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {
    @NotBlank
    private String name;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }


}
