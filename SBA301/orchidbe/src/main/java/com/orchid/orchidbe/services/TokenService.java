package com.orchid.orchidbe.services;

import com.orchid.orchidbe.pojos.Account;
import com.orchid.orchidbe.pojos.Token;

public interface TokenService {

    Token addToken(int userId, String token, boolean isMobileDevice);

    Token refreshToken(String refreshToken, Account user) throws Exception;

    void deleteToken(String token, Account user);

    Token findAccountByToken(String token);

}
