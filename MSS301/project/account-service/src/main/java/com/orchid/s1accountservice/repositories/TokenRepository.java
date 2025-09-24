package com.orchid.s1accountservice.repositories;

import com.orchid.s1accountservice.domain.token.Token;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findByAccountId(Long accountId);

    Optional<Token> findByToken(String token);

    Optional<Token> findByRefreshToken(String token);

}
