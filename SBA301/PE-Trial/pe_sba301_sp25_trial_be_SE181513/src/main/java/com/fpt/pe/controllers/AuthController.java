package com.fpt.pe.controllers;

import com.fpt.pe.apis.MyApiResponse;
import com.fpt.pe.models.SystemAccount;
import com.fpt.pe.services.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "auth", description = "Auth API")
public class AuthController {

    private final AccountService accountService;

    public record LoginDTO(
        String email,
        String password
    ){

    }

    public record LoginRes(
        String accessToken,
        SystemAccount account
    ){

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        return MyApiResponse.success(accountService.login(loginDTO));
    }
}
