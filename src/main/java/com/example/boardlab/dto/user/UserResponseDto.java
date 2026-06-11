package com.example.boardlab.dto.user;

import com.example.boardlab.domain.User;

/**
 * [클래스 역할] 회원 정보 조회(GET /users/{userId}) 시 패스워드와 같은 민감 정보를 제외하고 안전한 데이터만 응답하기 위한 객체입니다.
 */
public class UserResponseDto {
    private String email;
    private String nickname;
    private String profileImage;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
    }

    public String getEmail() { return email; }
    public String getNickname() { return nickname; }
    public String getProfileImage() { return profileImage; }
}