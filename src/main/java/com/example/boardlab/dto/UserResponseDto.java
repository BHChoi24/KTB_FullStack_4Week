package com.example.boardlab.dto;

import com.example.boardlab.domain.User;
import lombok.Getter;

/*
 * 회원 정보를 응답하기 위한 DTO
 * 비밀번호는 응답으로 보내지 않기 때문에 제외
 */
@Getter
public class UserResponseDto {

    private Long id;

    private String email;

    private String nickname;

    //User 객체를 UserResponseDto로 변환하는 생성자
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}