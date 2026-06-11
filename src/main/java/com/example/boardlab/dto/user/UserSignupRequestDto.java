package com.example.boardlab.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * [클래스 역할] 회원가입(POST /users/signup) 요청 시 입력 필드의 유효성을 일차적으로 검증
 */
public class UserSignupRequestDto {
    @NotBlank(message = "email_empty")
    @Email(message = "email_invalid")
    private String email;

    @NotBlank(message = "password_empty")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$",
            message = "password_invalid"
    )
    private String password;

    @NotBlank(message = "password_check_empty")
    private String passwordCheck;

    @NotBlank(message = "nickname_empty")
    @Size(max = 10, message = "nickname_invalid")
    private String nickname;

    private String profileImage; // 프로필 이미지는 공백을 허용하여 선택적으로 입력받음

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPasswordCheck() { return passwordCheck; }
    public String getNickname() { return nickname; }
    public String getProfileImage() { return profileImage; }
}
