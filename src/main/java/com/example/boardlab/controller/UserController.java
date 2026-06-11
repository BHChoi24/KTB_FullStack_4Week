package com.example.boardlab.controller;

import com.example.boardlab.domain.User;
import com.example.boardlab.dto.user.PasswordUpdateRequestDto;
import com.example.boardlab.dto.user.UserIdResponseDto;
import com.example.boardlab.dto.user.UserLoginRequestDto;
import com.example.boardlab.dto.user.UserLoginResponseDto;
import com.example.boardlab.dto.user.UserProfileUpdateRequestDto;
import com.example.boardlab.dto.user.UserResponseDto;
import com.example.boardlab.dto.user.UserSignupRequestDto;
import com.example.boardlab.dto.user.UserSignupResponseDto;
import com.example.boardlab.response.ApiResponse;
import com.example.boardlab.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 관련 HTTP 요청을 받는 클래스입니다.
 *
 * Controller의 역할은 다음 세 가지입니다.
 * 1. URL, 헤더, JSON 요청을 Java 값으로 받습니다.
 * 2. 실제 규칙 처리는 UserService에 요청합니다.
 * 3. 처리 결과를 API 응답 DTO로 바꾸어 반환합니다.
 */
@RestController
public class UserController {
    // 회원가입, 로그인, 회원 수정 같은 실제 규칙은 Service가 담당합니다.
    private final UserService userService;

    // 생성자 주입: Spring이 실행될 때 UserService 객체를 넣어 줍니다.
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 로그인 API: POST /users/login
     *
     * @RequestBody는 요청 JSON을 UserLoginRequestDto로 변환합니다.
     * @Valid는 DTO의 이메일 형식과 빈 값 여부를 검사합니다.
     */
    @PostMapping("/users/login")
    public ApiResponse<UserLoginResponseDto> login(
            @Valid @RequestBody UserLoginRequestDto request
    ) {
        // 1. 이메일과 비밀번호 확인은 Service에 맡깁니다.
        User user = userService.login(request.getEmail(), request.getPassword());

        // 2. 비밀번호를 제외하고 로그인에 필요한 정보만 응답합니다.
        return ApiResponse.of(
                "login_success",
                new UserLoginResponseDto("accessToken", user.getId(), user.getNickname())
        );
    }

    /**
     * 회원가입 API: POST /users/signup
     *
     * 회원이 새로 생성되었으므로 기본 200이 아니라 201 Created를 반환합니다.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/signup")
    public ApiResponse<UserSignupResponseDto> signup(
            @Valid @RequestBody UserSignupRequestDto request
    ) {
        // 1. 중복 이메일, 중복 닉네임, 비밀번호 확인까지 Service에서 검사합니다.
        User user = userService.signup(request);

        // 2. 생성된 사용자 번호를 명세 형식으로 반환합니다.
        return ApiResponse.of("user_add_success", new UserSignupResponseDto(user));
    }

    /**
     * 프로필 수정 API: PATCH /users/profile
     *
     * X-USER-ID는 Spring Security를 사용하지 않는 이번 과제의 임시 로그인 정보입니다.
     * required=false로 둔 이유는 헤더가 없을 때 Spring 기본 400 대신 Service에서 401을 만들기 위해서입니다.
     */
    @PatchMapping("/users/profile")
    public ApiResponse<UserIdResponseDto> updateProfile(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId,
            @Valid @RequestBody UserProfileUpdateRequestDto request
    ) {
        // Service가 로그인 여부와 닉네임 중복을 확인한 후 프로필을 변경합니다.
        User user = userService.updateProfile(userId, request);
        return ApiResponse.of("user_modify_success", new UserIdResponseDto(user.getId()));
    }

    /**
     * 회원 탈퇴 API: DELETE /users/profile
     *
     * 탈퇴할 때 사용자가 작성한 게시글과 댓글도 Service에서 함께 정리합니다.
     */
    @DeleteMapping("/users/profile")
    public ApiResponse<Void> deleteProfile(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId
    ) {
        userService.delete(userId);

        // 삭제 응답에는 추가 데이터가 필요하지 않으므로 data는 null입니다.
        return ApiResponse.of("delete_user_success", null);
    }

    /** 비밀번호 수정 API: PATCH /users/profile/password */
    @PatchMapping("/users/profile/password")
    public ApiResponse<UserIdResponseDto> updatePassword(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId,
            @Valid @RequestBody PasswordUpdateRequestDto request
    ) {
        // 로그인 여부, 비밀번호 형식, 비밀번호 확인 일치 여부를 검사한 뒤 변경합니다.
        userService.updatePassword(userId, request);
        return ApiResponse.of("password_change_success", new UserIdResponseDto(userId));
    }

    /** 과제 명세 외 보조 API: 더미 사용자 정보를 빠르게 확인할 때 사용합니다. */
    @GetMapping("/users/{userId}")
    public ApiResponse<UserResponseDto> getUser(@PathVariable Long userId) {
        // URL의 {userId} 값은 @PathVariable을 통해 메서드 파라미터로 들어옵니다.
        return ApiResponse.of("user_access_success", new UserResponseDto(userService.findById(userId)));
    }
}
