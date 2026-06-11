package com.example.boardlab.dto.user;

/**
 * [클래스 역할] 로그인 성공 시 시트 명세서에 정의된 JSON 포맷대로 응답 데이터를 내려주기 위한 객체입니다.
 */
public class UserLoginResponseDto {
    private String token;     // 클라이언트에게 발급할 인증 토큰 (과제 기준 "accessToken" 하드코딩)
    private Long user_id;     // 로그인에 성공한 사용자의 고유 ID 번호 (시트 기준 스네이크 케이스 포맷 준수)
    private String nickname;  // 로그인에 성공한 사용자의 닉네임

    public UserLoginResponseDto(String token, Long user_id, String nickname) {
        this.token = token;
        this.user_id = user_id;
        this.nickname = nickname;
    }

    public String getToken() { return token; }
    public Long getUser_id() { return user_id; }
    public String getNickname() { return nickname; }
}