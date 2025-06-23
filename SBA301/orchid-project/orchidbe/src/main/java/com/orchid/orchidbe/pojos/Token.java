package com.orchid.orchidbe.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"id", "token", "refreshToken", "tokenType", "expirationDate",
    "refreshExpirationDate", "isMobile", "revoked", "expired", "accountId"})
public class Token {

    @Id
    private String id;

    private String token;

    @Field("refresh_token")
    private String refreshToken;

    @Field("token_type")
    private String tokenType;

    @Field("expiration_date")
    private LocalDateTime expirationDate;

    @Field("refresh_expiration_date")
    private LocalDateTime refreshExpirationDate;

    @Field("is_mobile")
    private boolean isMobile;


    private boolean revoked;
    private boolean expired;

    @Field("account_id")
    private String accountId;

}
