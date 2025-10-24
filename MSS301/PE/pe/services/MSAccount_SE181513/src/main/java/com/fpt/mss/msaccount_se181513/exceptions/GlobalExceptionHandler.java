package com.fpt.mss.msaccount_se181513.exceptions;

import com.fpt.mss.api.MyApiResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<MyApiResponse<Object>> handleNullPointerException(NullPointerException e) {
        log.error("NullPointerException: ", e);
        return MyApiResponse.error(
            HttpStatus.BAD_REQUEST, "Null pointer exception occurred", e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MyApiResponse<Object>> handleIllegalArgumentException(
        IllegalArgumentException e) {
        log.error("IllegalArgumentException: ", e);
        return MyApiResponse.error(HttpStatus.BAD_REQUEST, "Invalid argument provided", e.getMessage());
    }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<MyApiResponse<Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            WebRequest request) {
            log.error("Validation error: {}", ex.getMessage());

            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(error ->
                                                               errors.put(error.getField(),
     error.getDefaultMessage()));

            return MyApiResponse.validationError(errors);
        }

}
