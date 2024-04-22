package com.stemm.pubsub.service.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

public record ApiResponse<T>(
    @JsonInclude(NON_EMPTY) String message,
    @JsonInclude(NON_EMPTY) T data,
    @JsonInclude(NON_EMPTY) T error
) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(null, data, null);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(message, null, null);
    }

    public static <T> ApiResponse<T> failure(String message, T data) {
        return new ApiResponse<>(message, data, null);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(message, null, null);
    }

    public static <T> ApiResponse<T> error(String message, T error) {
        return new ApiResponse<>(message, null, error);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, null, null);
    }
}
