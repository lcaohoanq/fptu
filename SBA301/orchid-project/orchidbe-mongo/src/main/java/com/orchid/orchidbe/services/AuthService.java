package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.AuthPort;
import com.orchid.orchidbe.dto.AuthPort.LoginReq;
import com.orchid.orchidbe.dto.TokenPort;
import com.orchid.orchidbe.pojos.Account;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    Account getUserDetailsFromRefreshToken(String refreshToken) throws Exception;
    Account getUserDetailsFromToken(String token) throws Exception;
    AuthPort.LoginResponse login(LoginReq loginReq, HttpServletRequest request);
    AuthPort.LoginResponse refreshToken(TokenPort.RefreshTokenDTO refreshTokenDTO) throws Exception;
    void logout(HttpServletRequest request);

}
