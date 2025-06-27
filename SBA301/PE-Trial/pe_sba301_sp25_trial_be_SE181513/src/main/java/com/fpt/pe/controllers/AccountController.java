package com.fpt.pe.controllers;

import com.fpt.pe.apis.MyApiResponse;
import com.fpt.pe.repositories.AccountRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/sys-accounts")
@RestController
@RequiredArgsConstructor
@Tag(name = "sys-accounts", description = "System Account API")
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return MyApiResponse.success(accountRepository.findAll());
    }

}
