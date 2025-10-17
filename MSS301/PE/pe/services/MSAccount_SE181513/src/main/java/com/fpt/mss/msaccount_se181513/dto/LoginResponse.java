package com.fpt.mss.msaccount_se181513.dto;

import com.fpt.mss.msaccount_se181513.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private Long refreshExpiresIn;
    private Account user;
}