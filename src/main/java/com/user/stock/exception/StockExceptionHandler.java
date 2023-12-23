package com.user.stock.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.Map;

@RestControllerAdvice
public class StockExceptionHandler {

    @ExceptionHandler(value = StockNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoStockFound(
            HttpServletRequest request) {
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "path", request.getRequestURI());

        // 404エラーを返す
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);

    }
}
