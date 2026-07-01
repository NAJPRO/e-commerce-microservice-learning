package com.orders.orderservice.exception;

public class NoStockException extends RuntimeException {
    public NoStockException(String message) {
        super(message);
    }

    public NoStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoStockException() {
        super("No stock available");
    }

}
