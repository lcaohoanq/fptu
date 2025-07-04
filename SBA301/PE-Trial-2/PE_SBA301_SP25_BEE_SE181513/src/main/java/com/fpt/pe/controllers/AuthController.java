package com.fpt.pe.controllers;

import com.fpt.pe.apis.MyApiResponse;
import com.fpt.pe.models.AccountMember;
import com.fpt.pe.services.AccountService;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(name = "email", description = "Email of the user", example = "ad@gmail.com")
        String email,
        @Schema(name = "password", description = "Password of the user", example = "1")
        String password
    ){

    }

    public record LoginRes(
        String accessToken,
        AccountMember account
    ){

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        return MyApiResponse.success(accountService.login(loginDTO));
    }
}
