package com.example.boardlab.response;

import java.util.List;

/**
 * [클래스 역할] 유효성 검증(Validation) 실패 시 반환되는 최상위 에러 전용 JSON 구조체 가방입니다.
 * 시트에 표기된 "message", "data": null, "errors": [...] 배열 구조를 온전히 재현합니다.
 */
public class ErrorResponse {
    private String message;                 // 에러 대분류 메시지 (과제용 고정값: "invalid_input_value")
    private Object data;                    // 에러 발생 시 데이터 바디는 항상 규격상 null 처리
    private List<ValidationError> errors;   // 세부 입력 항목별 유효성 미달 원인 리스트 컬렉션

    public ErrorResponse(String message, List<ValidationError> errors) {
        this.message = message;
        this.data = null; // 에러 응답 규격 준수를 위해 null 강제 주입
        this.errors = errors;
    }

    public String getMessage() { return message; }
    public Object getData() { return data; }
    public List<ValidationError> getErrors() { return errors; }
}