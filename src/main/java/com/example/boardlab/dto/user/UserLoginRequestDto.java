package com.example.boardlab.dto.user;

import jakarta.validation.constraints.NotBlank;

public class UserLoginRequestDto {
    @NotBlank(message = "email_empty")
    private String email;

    @NotBlank(message = "password_empty")
    private String password;

    public UserLoginRequestDto() {}

    public UserLoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
}