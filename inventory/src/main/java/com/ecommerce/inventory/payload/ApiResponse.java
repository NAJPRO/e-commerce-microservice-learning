package com.ecommerce.inventory.payload;


import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        T data,
        String status,
        int code,
        String message,
        Instant timestamp) {

    public static <T> ApiResponse<T> success(int code, String message, T data) {
        return new ApiResponse<T>(
                data,
                "Success",
                code,
                message,
                Instant.now());
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<T>(
                null,
                "Error",
                code,
                message,
                Instant.now());
    }
}