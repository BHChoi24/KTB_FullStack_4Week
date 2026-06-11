package com.example.boardlab.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * 회원 가입 요청 DTO
 * 필요한건 이메일, 비밀번호, 이름
 */
@Getter
@NoArgsConstructor
public class UserRequestDto {

    private String email;
    private String password;
    private String nickname;
}
