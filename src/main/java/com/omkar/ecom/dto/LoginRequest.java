package com.omkar.ecom.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Username required")
    private String username;

    @NotBlank(message = "Password required")
    private String password;
}
