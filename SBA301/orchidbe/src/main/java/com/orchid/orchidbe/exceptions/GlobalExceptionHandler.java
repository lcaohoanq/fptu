package com.orchid.orchidbe.exceptions;

import com.orchid.orchidbe.apis.MyApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<MyApiResponse<Object>> handleNullPointerException
        (
        NullPointerException e) {
        log.error("NullPointerException: ", e);
        return MyApiResponse.error(
            HttpStatus.BAD_REQUEST,
            "Null pointer exception occurred",
            e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MyApiResponse<Object>> handleIllegalArgumentException(
        IllegalArgumentException e) {
        log.error("IllegalArgumentException: ", e);
        return MyApiResponse.error(
            HttpStatus.BAD_REQUEST,
            "Invalid argument provided",
            e.getMessage());
    }

}
