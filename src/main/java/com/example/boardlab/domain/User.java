package com.example.boardlab.domain;

import lombok.Getter;

//유저 정보 클래스

@Getter
public class User {

    private Long id;
    private String email;
    private String password;
    private String nickname;

    public User(Long id,
                String email,
                String password,
                String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}