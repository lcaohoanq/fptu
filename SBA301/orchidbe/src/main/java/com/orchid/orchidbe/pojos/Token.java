package com.orchid.orchidbe.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document(value = "tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {

    @Id
    private Integer id;

    private String token;

    private String refreshToken;

    private String tokenType;

    private LocalDateTime expirationDate;

    private LocalDateTime refreshExpirationDate;

    private boolean isMobile;

    private boolean revoked;
    private boolean expired;

    private String accountId;

}
