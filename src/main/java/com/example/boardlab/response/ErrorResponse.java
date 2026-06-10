package com.example.boardlab.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

//입력값 검증 실패 응답을 위한 클래스
@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String message;
    private final Object data;
    private final List<ValidationError> errors;
}