package com.example.boardlab.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {

    private String email;
    private String password;

    @JsonProperty("password_check")
    private String passwordCheck;

    private String nickname;

    @JsonProperty("profile_image")
    private String profileImage;
}