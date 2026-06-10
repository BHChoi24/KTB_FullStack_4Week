package com.example.boardlab.domain;

import lombok.Getter;

@Getter
public class User {

    private Long id;
    private String email;
    private String password;
    private String nickname;

    // 회원가입 시 선택으로 받는 프로필 이미지
    private String profileImage;

    public User(Long id, String email, String password, String nickname, String profileImage) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}