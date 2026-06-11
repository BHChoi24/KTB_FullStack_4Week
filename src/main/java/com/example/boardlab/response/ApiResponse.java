package com.example.boardlab.response;

/**
 * [클래스 역할] 서버가 반환하는 모든 성공 및 일반 실패(401, 403, 404) 응답의 공통 최상위 포맷 껍데기 클래스입니다.
 * Postman 결과창에 일관된 단일 규격의 JSON 모양을 뿌려주기 위해 모든 컨트롤러의 반환형을 캡슐화합니다.
 */
public class ApiResponse<T> {
    private String message; // 클라이언트에게 전달할 성공/실패 관련 고유 제어 태그 코드 (ex> "login_success")
    private T data;         // 실제 결과 데이터셋이 실리는 제네릭 가방 공간

    private ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    /**
     * 성공 또는 일반 단건형 응답 팩토리 메서드
     */
    public static <T> ApiResponse<T> of(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    public String getMessage() { return message; }
    public T getData() { return data; }
}