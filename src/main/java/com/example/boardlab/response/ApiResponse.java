package com.example.boardlab.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
 * 모든 API 응답 형식을 통일하기 위한 클래스
 * 성공 응답과 실패 응답을 같은 구조로 내려주기 위해 사용한다.
 */
@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    // 응답 상태 메시지
    // 예: "success", "user_not_found"
    private final String message;

    // 실제 응답 데이터
    // 성공 시에는 객체(데이터)가 들어가고, 실패 시에는 null이 들어갈 수 있다.
    private final T data;

    /*
     * 성공 응답을 쉽게 만들기 위한 메서드
     * Controller에서 ApiResponse.success(data) 형태로 사용할 수 있다.
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", data);
    }

    public static <T> ApiResponse<T> of(String message, T data) {
        return new ApiResponse<>(message, data);
    }
}