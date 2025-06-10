package com.orchid.orchidbe.controllers;

import com.orchid.orchidbe.apis.MyApiResponse;
import com.orchid.orchidbe.dto.AccountDTO;
import com.orchid.orchidbe.pojos.Account;
import com.orchid.orchidbe.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/accounts")
@RequiredArgsConstructor
@Tag(name = "Account Api", description = "Operation related to Account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("")
    @Operation(summary = "Get all accounts", description = "Returns a list of all accounts")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all accounts")
    public ResponseEntity<MyApiResponse<List<Account>>> getAccounts() {
        return MyApiResponse.success(accountService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get all accounts", description = "Returns a list of all accounts")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all accounts")
    public ResponseEntity<MyApiResponse<Account>> getAccountById(@PathVariable int id) {
        return MyApiResponse.success(accountService.getById(id));
    }

    @PostMapping("")
    @Operation(summary = "Create new account", description = "Creates a new account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Account created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or email already exists")
    })
    public ResponseEntity<MyApiResponse<Object>> createAccount(
        @RequestBody AccountDTO.AccountReq accountReq
    ) {
        accountService.add(accountReq);
        return MyApiResponse.created();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update account", description = "Updates an existing account by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or email already exists"),
        @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<MyApiResponse<Object>> updateAccount(
        @PathVariable int id,
        @RequestBody AccountDTO.AccountReq accountReq
    ) {
        accountService.update(id, accountReq);
        return MyApiResponse.updated();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account", description = "Deletes an account by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Account deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<MyApiResponse<Object>> deleteAccount(@PathVariable int id) {
        accountService.delete(id);
        return MyApiResponse.noContent();
    }

}
