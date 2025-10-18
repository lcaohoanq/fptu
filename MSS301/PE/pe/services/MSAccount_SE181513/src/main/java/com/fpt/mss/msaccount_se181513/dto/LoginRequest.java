package com.fpt.mss.msaccount_se181513.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Schema(description = "User email", example = "admin@blindboxes.vn")
    private String email;
    @Schema(description = "User password", example = "@2")
    private String password;
}