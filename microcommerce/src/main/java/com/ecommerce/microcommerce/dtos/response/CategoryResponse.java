package com.ecommerce.microcommerce.dtos.response;

public class CategoryResponse {
    private String name;
    private String id;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
