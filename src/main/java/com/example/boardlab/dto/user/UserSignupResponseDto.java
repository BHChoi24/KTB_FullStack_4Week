package com.example.boardlab.dto.user;

import com.example.boardlab.domain.User;

/** 회원가입이 완료된 사용자의 ID를 반환합니다. */
public class UserSignupResponseDto {
    private Long userId;

    public UserSignupResponseDto(User user) {
        this.userId = user.getId();
    }

    public Long getUserId() { return userId; }
}
