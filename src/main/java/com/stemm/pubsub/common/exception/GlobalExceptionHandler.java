package com.stemm.pubsub.common.exception;

import com.stemm.pubsub.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static com.stemm.pubsub.common.ApiResponse.error;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Dto validation 예외 처리를 수행하며, 모든 필드 오류를 반환합니다.
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<List<String>>> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.toList());

        log.error("validation error: {}", errors);

        return ResponseEntity
            .badRequest()
            .body(error("Validation 예외가 발생했습니다.", errors));
    }

    /**
     * 존재하지 않는 유저에 대한 예외를 처리합니다.
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> handleUserNotFoundException(UserNotFoundException e) {
        log.error("존재하지 않는 유저입니다. user id = {}", e.getUserId());

        return ResponseEntity
            .badRequest()
            .body(error(e.getMessage()));
    }

    /**
     * 인가되지 않은 유저에 대한 예외를 처리합니다.
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedUserException(UnauthorizedUserException e) {
        log.error("권한이 없는 유저입니다. user id = {}", e.getUserId());

        return ResponseEntity
            .status(FORBIDDEN)
            .body(error(e.getMessage()));
    }
}
