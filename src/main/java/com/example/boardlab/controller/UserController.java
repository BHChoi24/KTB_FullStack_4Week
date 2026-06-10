package com.example.boardlab.controller;

import com.example.boardlab.domain.User;
import com.example.boardlab.dto.user.UserRequestDto;
import com.example.boardlab.dto.user.UserResponseDto;
import com.example.boardlab.response.ApiResponse;
import com.example.boardlab.service.UserService;

//import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



//유저 처리 컨트롤러

//HTTP 요청을 처리하는 컨트롤러이다
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ApiResponse<UserResponseDto> signup(
            @RequestBody UserRequestDto requestDto
    ) {

        User user = userService.signup(requestDto);

        UserResponseDto responseDto =
                new UserResponseDto(user);

        return ApiResponse.success(responseDto);
    }

    // 회원 단건 조회 API
    // GET /users/{userId}, URL값을 변수로 받기
    @GetMapping("/users/{userId}")
    public ApiResponse<UserResponseDto> getUser(
            @PathVariable Long userId
    ) {

        //회원 찾는 로직
        User user = userService.findById(userId);

        UserResponseDto responseDto = new UserResponseDto(user);

        return ApiResponse.success(responseDto);
    }
}