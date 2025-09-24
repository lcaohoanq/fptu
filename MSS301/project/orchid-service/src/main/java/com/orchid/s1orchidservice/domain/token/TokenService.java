package com.orchid.s1orchidservice.domain.token;

import com.orchid.s1orchidservice.domain.account.Account;

public interface TokenService {

    Token addToken(Long userId, String token, boolean isMobileDevice);

    Token refreshToken(String refreshToken, Account user) throws Exception;

    void deleteToken(String token, Account user);

    Token findAccountByToken(String token);

}
