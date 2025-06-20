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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@Tag(name = "🔐 Authentication", description = """
    Authentication endpoints for login, logout, and token management.
    
    **Quick Start:**
    1. Use POST /login to get your JWT token
    2. Copy the token from the response
    3. Click the 🔒 Authorize button above
    4. Enter: Bearer YOUR_TOKEN
    5. Now you can access protected endpoints!
    """)
public class AuthController {

    AccountService userService;
    TokenService tokenService;
    private final AuthService authService;

    @Operation(
        summary = "🚀 Login to get JWT token",
        description = """
            **Login and get your authentication token**
            
            ### How to use this token in Swagger:
            1. **Execute this login request** with your credentials
            2. **Copy the `token` value** from the response (the long string starting with 'eyJ...')
            3. **Click the 🔒 Authorize button** at the top of this page
            4. **Enter:** `Bearer YOUR_COPIED_TOKEN` (include the word "Bearer" and a space)
            5. **Click Authorize** and then Close
            6. **You're now authenticated!** Try any protected endpoint.
            
            ### Example Response:
            The response will contain a `token` field - this is what you need to copy for authorization.
            
            ### Token expires based on the `expirationDate` in the response.
            """,
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Login credentials",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = LoginReq.class),
                examples = {
                    @ExampleObject(
                        name = "Demo User",
                        summary = "Example login",
                        description = "Sample credentials for testing",
                        value = """
                            {
                              "email": "mnhw.0612@gmail.com",
                              "password": "string"
                            }
                            """
                    )
                }
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Login successful - Copy the token value for authorization!",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MyApiResponse.class),
                examples = @ExampleObject(
                    name = "Success Response",
                    description = "Copy the 'token' value and use it with 'Bearer ' prefix in the Authorize button",
                    value = """
                        {
                          "code": 200,
                          "message": "Success",
                          "data": {
                            "tokenResponse": {
                              "id": "12345",
                              "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                              "refreshToken": "refresh_token_here",
                              "tokenType": "Bearer",
                              "expirationDate": "2024-12-31T23:59:59",
                              "refreshExpirationDate": "2025-01-31T23:59:59",
                              "mobile": false,
                              "revoked": false,
                              "expired": false
                            }
                          }
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "❌ Invalid credentials"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "❌ Server error"
        )
    })
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

    @Operation(
        summary = "🚪 Logout and invalidate token",
        description = """
            **Logout and invalidate your current JWT token**
            
            This endpoint requires authentication. Make sure you're logged in first.
            After logout, you'll need to login again to access protected endpoints.
            """,
        security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "✅ Logout successful"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "❌ Unauthorized - Token invalid or missing"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "❌ Token not found"
        )
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_STAFF')")
    @PostMapping("/logout")
    public ResponseEntity<MyApiResponse<Object>> logout(
        HttpServletRequest request
    ){
        var authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            throw new TokenNotFoundException("Token not found");
        }

        var token = authorization.substring(7);
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userService.getByEmail(userDetails.getUsername());

        authService.logout(token, user);

        return MyApiResponse.noContent();
    }
}