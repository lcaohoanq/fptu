package com.fpt.mss.msaccount_se181513.domain.auth;

import com.fpt.mss.msaccount_se181513.dto.LoginRequest;
import com.fpt.mss.msaccount_se181513.dto.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest, HttpServletRequest request);

}
