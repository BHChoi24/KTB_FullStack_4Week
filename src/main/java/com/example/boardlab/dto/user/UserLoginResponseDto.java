package com.example.boardlab.dto.user;

public class UserLoginResponseDto {
    private String token;
    private Long user_id;
    private String nickname;

    public UserLoginResponseDto(String token, Long user_id, String nickname) {
        this.token = token;
        this.user_id = user_id;
        this.nickname = nickname;
    }

    public String getToken() { return token; }
    public Long getUser_id() { return user_id; }
    public String getNickname() { return nickname; }
}