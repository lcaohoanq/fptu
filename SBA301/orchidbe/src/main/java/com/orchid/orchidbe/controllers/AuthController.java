package com.orchid.orchidbe.controllers;

import com.orchid.orchidbe.apis.MyApiResponse;
import com.orchid.orchidbe.dto.AuthPort;
import com.orchid.orchidbe.dto.AuthPort.LoginResponse;
import com.orchid.orchidbe.dto.LoginReq;
import com.orchid.orchidbe.dto.TokenPort.TokenResponse;
import com.orchid.orchidbe.exceptions.TokenNotFoundException;
import com.orchid.orchidbe.pojos.Account;
import com.orchid.orchidbe.pojos.Token;
import com.orchid.orchidbe.services.AccountService;
import com.orchid.orchidbe.services.AuthService;
import com.orchid.orchidbe.services.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<MyApiResponse<Object>> login(
        @RequestBody @Valid
        LoginReq loginReq,
        HttpServletRequest request) throws Exception {

        log.info("Login body received: {}", loginReq);

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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_STAFF')")
    @PostMapping("/logout")
    public ResponseEntity<MyApiResponse<Object>> logout(
        HttpServletRequest request
    ){
        var authorization = request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Bearer ")) {
            throw new TokenNotFoundException("Token not found");
        }

        var token = authorization.substring(7);
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userService.getByEmail(userDetails.getUsername());

        authService.logout(token, user);

        return MyApiResponse.noContent();
    }

}
