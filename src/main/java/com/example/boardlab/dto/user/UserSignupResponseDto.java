package com.example.boardlab.dto.user;

import com.example.boardlab.domain.User;

/**
 * [클래스 역할] 회원가입 완료 후 새로 가입된 사용자의 고유 ID를 반환하기 위한 응답 가방입니다.
 */
public class UserSignupResponseDto {
    private Long id; // 가입 완료된 유저의 고유 식별 번호

    public UserSignupResponseDto(User user) {
        this.id = user.getId(); // 원본 User 도메인에서 안전하게 ID만 추출하여 세팅
    }

    public Long getId() { return id; }
}