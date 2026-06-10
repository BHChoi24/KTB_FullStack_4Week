package com.example.boardlab.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {

    @NotBlank(message = "email_empty")
    @Email(message = "email_invalid")
    private String email;

    @NotBlank(message = "password_empty")
    private String password;

    @JsonProperty("password_check")
    @NotBlank(message = "password_check_empty")
    private String passwordCheck;

    @NotBlank(message = "nickname_empty")
    private String nickname;

    @JsonProperty("profile_image")
    private String profileImage;
}