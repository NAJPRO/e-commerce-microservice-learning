package com.ecommerce.microcommerce.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

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