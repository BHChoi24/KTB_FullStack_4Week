package com.example.boardlab.dto.user;

import jakarta.validation.constraints.NotBlank;

/**
 * [클래스 역할] 회원가입(POST /users/signup) 요청 시 입력 필드의 유효성을 일차적으로 검증
 */
public class UserSignupRequestDto {
    @NotBlank(message = "email_empty")
    private String email;

    @NotBlank(message = "password_empty")
    private String password;

    @NotBlank(message = "nickname_empty")
    private String nickname;

    private String profileImage; // 프로필 이미지는 공백을 허용하여 선택적으로 입력받음

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getNickname() { return nickname; }
    public String getProfileImage() { return profileImage; }
}