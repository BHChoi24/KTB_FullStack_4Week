package com.example.boardlab.controller;

import com.example.boardlab.domain.User;
import com.example.boardlab.dto.user.UserResponseDto;
import com.example.boardlab.dto.user.UserSignupRequestDto;
import com.example.boardlab.dto.user.UserSignupResponseDto;
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

    // 회원가입 API
    // POST /users/signup
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/signup")
    public ApiResponse<UserSignupResponseDto> signup(
            @Valid@RequestBody UserSignupRequestDto requestDto
    ) {

        User user = userService.signup(requestDto);

        UserSignupResponseDto responseDto =
                new UserSignupResponseDto(user);

        return ApiResponse.of(
                "user_add_success",
                responseDto
        );
    }

    // 회원 단건 조회 API
    // GET /users/{userId}
    @GetMapping("/users/{userId}")
    public ApiResponse<UserResponseDto> getUser(
            @PathVariable Long userId
    ) {

        User user = userService.findById(userId);

        UserResponseDto responseDto =
                new UserResponseDto(user);

        return ApiResponse.of(
                "user_access_success",
                responseDto
        );
    }
}