package com.ecommerce.microcommerce.exception;

public class ProductExistBySkuException extends RuntimeException{
    public ProductExistBySkuException(String msg){
        super(msg);
    }
}
