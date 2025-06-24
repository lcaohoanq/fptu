package com.orchid.orchidbe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.orchid.orchidbe.dto.TokenPort.TokenResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public interface AuthPort {

    @JsonInclude(Include.NON_NULL)
    record LoginResponse(
        TokenResponse token,
        AccountDTO.AccountCompactRes account
    ) {

    }

    record LoginReq(
        @Email(message = "Email is not valid")
        @Schema(description = "User email", example = "mnhw.0612@gmail.com")
        String email,

        @NotBlank(message = "Password is required")
        @Schema(description = "User password", example = "string")
        String password
    ) {

    }

}
