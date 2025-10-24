package com.fpt.mss.msaccount_se181513.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.mss.api.MyApiResponse;
import com.fpt.mss.api.MyApiResponse.Error;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
  private final ObjectMapper objectMapper;

  @Override
  public void handle(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
      throws IOException {
    response.setContentType("application/json");
    response.setStatus(HttpStatus.FORBIDDEN.value());

    MyApiResponse<Object> errorResponse =
        new Error<>(
            HttpStatus.FORBIDDEN.value(),
            "Access Denied",
            "You don't have permission to access this resource",
            request.getRequestURI(),
            Instant.now());

    objectMapper.writeValue(response.getOutputStream(), errorResponse);
  }
}
