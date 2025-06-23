package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.AuthPort;
import com.orchid.orchidbe.dto.TokenPort;
import com.orchid.orchidbe.pojos.Account;

public interface AuthService {

    AuthPort.LoginResponse refreshToken(TokenPort.RefreshTokenDTO refreshTokenDTO) throws Exception;
    void logout(String token , Account account);

}
