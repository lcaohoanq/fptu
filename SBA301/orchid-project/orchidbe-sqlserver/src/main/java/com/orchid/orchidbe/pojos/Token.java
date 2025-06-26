package com.orchid.orchidbe.pojos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"id", "token", "refreshToken", "tokenType", "expirationDate",
    "refreshExpirationDate", "isMobile", "revoked", "expired", "accountId"})
public class Token {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
