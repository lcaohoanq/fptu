package com.orchid.orchidbe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AccountDTO {

    public record CreateAccountReq(
        String name,

        @Email(message = "Email is not valid")
        @NotBlank(message = "Email is required")
        @Schema(description = "User email", example = "mnhw.0612@gmail.com")
        String email,

        String password
    ) {

    }

    public record UpdateAccountReq(
        String name,

        @Email(message = "Email is not valid")
        @NotBlank(message = "Email is required")
        @Schema(description = "User email", example = "mnhw.0612@gmail.com")
        String email,

        String password,
        Integer roleId
    ) {

    }

    public record AccountResp(
        int id,
        String name,
        String email
    ){

    }

}
