package com.orchid.orchidbe.controllers;

import com.orchid.orchidbe.apis.MyApiResponse;
import com.orchid.orchidbe.dto.AuthPort;
import com.orchid.orchidbe.dto.AuthPort.LoginResponse;
import com.orchid.orchidbe.dto.TokenPort.TokenResponse;
import com.orchid.orchidbe.pojos.Account;
import com.orchid.orchidbe.pojos.Token;
import com.orchid.orchidbe.services.AccountService;
import com.orchid.orchidbe.services.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Authentication", description = "APIs for authentication including login, register, logout, and token refresh")
public class AuthController {

    AccountService userService;
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<MyApiResponse<Object>> login(
        @RequestBody @Valid
        AuthPort.LoginReq loginReq,
        HttpServletRequest request) throws Exception {

        String token = userService.login(loginReq.email(), loginReq.password());
        String userAgent = request.getHeader("User-Agent");
        Account userDetail = userService.getUserDetailsFromToken(token);
        Token jwtToken = tokenService.addToken(userDetail.getId(), token,
                                               isMobileDevice(userAgent));
        log.info("User logged in successfully");
        return MyApiResponse.success(new LoginResponse(new TokenResponse(
                                         jwtToken.getId(),
                                         jwtToken.getToken(),
                                         jwtToken.getRefreshToken(),
                                         jwtToken.getTokenType(),
                                         jwtToken.getExpirationDate(),
                                         jwtToken.getRefreshExpirationDate(),
                                         jwtToken.isMobile(),
                                         jwtToken.isRevoked(),
                                         jwtToken.isExpired()))
        );
    }

    private boolean isMobileDevice(String userAgent) {
        // Kiểm tra User-Agent header để xác định thiết bị di động
        if (userAgent == null) {
            return false;
        }
        return userAgent.toLowerCase().contains("mobile");
    }

}
