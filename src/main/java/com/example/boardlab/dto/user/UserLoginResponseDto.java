package com.example.boardlab.dto.user;

/**
 * [클래스 역할] 로그인 성공 시 시트 명세서에 정의된 JSON 포맷대로 응답 데이터를 내려주기 위한 객체입니다.
 */
public class UserLoginResponseDto {
    private String token;     // 클라이언트에게 발급할 인증 토큰 (과제 기준 "accessToken" 하드코딩)
    private Long userId;
    private String nickname;  // 로그인에 성공한 사용자의 닉네임

    public UserLoginResponseDto(String token, Long userId, String nickname) {
        this.token = token;
        this.userId = userId;
        this.nickname = nickname;
    }

    public String getToken() { return token; }
    public Long getUserId() { return userId; }
    public String getNickname() { return nickname; }
}
