package com.fpt.pe.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"id", "token", "refreshToken", "tokenType", "expirationDate",
    "refreshExpirationDate", "isMobile", "revoked", "expired", "accountId"})
public class Token {

    private Long id;

    private String token;
    private String refreshToken;
    private String tokenType;
    private LocalDateTime expirationDate;
    private LocalDateTime refreshExpirationDate;
    private boolean isMobile;
    private boolean revoked;
    private boolean expired;
    private Long accountId;

}
