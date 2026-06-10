package com.example.boardlab.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//입력값 검증 실패 -> 어떤 필드에서 어떤 문제가 발생했는지 표현하는 클래스
@Getter
@RequiredArgsConstructor
public class ValidationError {

    private final String field;
    private final String reason;
}