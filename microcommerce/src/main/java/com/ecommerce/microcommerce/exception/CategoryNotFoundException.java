package com.ecommerce.microcommerce.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String id){
        super("Category with ID : " + id + " not exist");
    }
}
