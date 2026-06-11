package com.example.boardlab.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PasswordUpdateRequestDto {
    @NotBlank(message = "password_empty")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$",
            message = "password_invalid"
    )
    private String password;

    @NotBlank(message = "password_check_empty")
    private String passwordCheck;

    public String getPassword() { return password; }
    public String getPasswordCheck() { return passwordCheck; }
}
