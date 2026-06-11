package com.example.boardlab.controller;

import com.example.boardlab.domain.User;
import com.example.boardlab.dto.user.*;
import com.example.boardlab.response.ApiResponse;
import com.example.boardlab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 시트 반영: 로그인 API 추가 구현 완료
    @PostMapping("/users/login")
    public ApiResponse<UserLoginResponseDto> login(
            @Valid @RequestBody UserLoginRequestDto requestDto
    ) {
        User user = userService.login(requestDto.getEmail(), requestDto.getPassword());
        UserLoginResponseDto responseDto = new UserLoginResponseDto("accessToken", user.getId(), user.getNickname());
        return ApiResponse.of("login_success", responseDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/signup")
    public ApiResponse<UserSignupResponseDto> signup(
            @Valid @RequestBody UserSignupRequestDto requestDto
    ) {
        User user = userService.signup(requestDto);
        UserSignupResponseDto responseDto = new UserSignupResponseDto(user);
        return ApiResponse.of("user_add_success", responseDto);
    }

    @GetMapping("/users/{userId}")
    public ApiResponse<UserResponseDto> getUser(
            @PathVariable Long userId
    ) {
        User user = userService.findById(userId);
        UserResponseDto responseDto = new UserResponseDto(user);
        return ApiResponse.of("user_access_success", responseDto);
    }
}