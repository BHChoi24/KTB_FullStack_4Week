package com.example.boardlab.response;

/**
 * [클래스 역할] 시트 명세서의 400 invalid_input_value 에러 구조를 완벽히 충족하기 위해 정의된 하위 에러 정보 객체입니다.
 * 어떤 입력 필드가 잘못되었고 사유가 무엇인지 한 쌍으로 묶는 역할을 담당합니다.
 */
public class ValidationError {
    private String field;   // 유효성 검증이 실패한 DTO 필드명 (ex> "email", "password")
    private String reason;  // 검증 실패 원인 코드 문자열 (ex> "email_empty", "password_empty")

    public ValidationError(String field, String reason) {
        this.field = field;
        this.reason = reason;
    }

    public String getField() { return field; }
    public String getReason() { return reason; }
}