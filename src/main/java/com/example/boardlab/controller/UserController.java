package com.example.boardlab.controller;

import com.example.boardlab.domain.User;
import com.example.boardlab.dto.user.*;
import com.example.boardlab.response.ApiResponse;
import com.example.boardlab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * [클래스 역할] 회원 관련 HTTP 요청(로그인, 회원가입, 회원조회)을 처리하는 컨트롤러입니다.
 */
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * [로그인 API] POST /users/login
     * 시트 기준 요구사항 반영: 이메일과 패스워드를 검증하고 토큰 및 회원정보를 반환합니다.
     * 실패 시 401(email_password_check) 에러를 발생시킵니다.
     */
    @PostMapping("/users/login")
    public ApiResponse<UserLoginResponseDto> login(
            @Valid @RequestBody UserLoginRequestDto requestDto
    ) {
        User user = userService.login(requestDto.getEmail(), requestDto.getPassword());
        UserLoginResponseDto responseDto = new UserLoginResponseDto("accessToken", user.getId(), user.getNickname());
        return ApiResponse.of("login_success", responseDto);
    }

    /**
     * [회원가입 API] POST /users/signup
     * 새로운 회원을 등록합니다. 정상 등록 시 201 Created 상태코드를 반환합니다.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/signup")
    public ApiResponse<UserSignupResponseDto> signup(
            @Valid @RequestBody UserSignupRequestDto requestDto
    ) {
        User user = userService.signup(requestDto);
        UserSignupResponseDto responseDto = new UserSignupResponseDto(user);
        return ApiResponse.of("user_add_success", responseDto);
    }

    /**
     * [회원 단건조회 API] GET /users/{userId}
     * 특정 식별자(ID)를 가진 회원의 상세 정보를 조회합니다. 존재하지 않을 시 404를 반환합니다.
     */
    @GetMapping("/users/{userId}")
    public ApiResponse<UserResponseDto> getUser(
            @PathVariable Long userId
    ) {
        User user = userService.findById(userId);
        UserResponseDto responseDto = new UserResponseDto(user);
        return ApiResponse.of("user_access_success", responseDto);
    }
}