package com.orchid.orchidbe.dto;

import com.orchid.orchidbe.pojos.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public interface AccountDTO {

    record CreateAccountReq(
        String name,

        @Email(message = "Email is not valid")
        @NotBlank(message = "Email is required")
        @Schema(description = "User email", example = "mnhw.0612@gmail.com")
        String email,

        String password
    ) {

    }

    record UpdateAccountReq(
        String name,

        @Email(message = "Email is not valid")
        @NotBlank(message = "Email is required")
        @Schema(description = "User email", example = "mnhw.0612@gmail.com")
        String email,

        String password,
        String roleId
    ) {

    }

    record AccountResp(
        String id,
        String name,
        String email
    ) {

        public static AccountResp fromEntity(
            Account account
        ) {
            return new AccountResp(account.getId(), account.getName(),
                                   account.getEmail());
        }

    }

}
