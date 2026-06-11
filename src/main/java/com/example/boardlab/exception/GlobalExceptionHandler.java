package com.example.boardlab.exception;

import com.example.boardlab.response.ApiResponse;
import com.example.boardlab.response.ErrorResponse;
import com.example.boardlab.response.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * [클래스 역할] 시스템 전역에서 예외(오류)가 터지는 순간 실행 흐름을 낚아채서
 * 시트(명세서) 요구 규격에 맞는 깔끔한 JSON 에러 구조로 정제하여 내보내는 공통 중앙 집중식 컨트롤러 어드바이스입니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * [404 리소스 부재 처리] NotFoundException 발생 시 가로챕니다.
     * posts_not_found, comment_not_found 등의 메세지와 함께 HTTP 404 Not Found 코드를 반환합니다.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<Void> handleNotFoundException(NotFoundException e) {
        return ApiResponse.of(e.getMessage(), null);
    }

    /**
     * [400 입력값 유효성 실패 처리] DTO에 부착된 @NotBlank 등의 조건 검증이 누락되었을 때 발생하는 핵심 에러 처리기입니다.
     * 발생 시 발생 대상 필드와 원인 코드를 ValidationError 배열로 추출하여 시트 전용 ErrorResponse 포맷으로 출력합니다.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        // 발생한 에러 필드와 바인딩 메시지 코드를 맵 구조 스트림으로 수집
        List<ValidationError> validationErrors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                .toList();

        return new ErrorResponse("invalid_input_value", validationErrors);
    }

    /**
     * [401 인증 또는 403 권한 실패 공통 처리] IllegalArgumentException 가로채기 지점입니다.
     * 서비스 레이어에서 수동 대조 후 던진 'email_password_check'(401추정) 및 'forbidden_author'(403추정)를 흡수합니다.
     * 단순 구현 환경이므로 클라이언트가 예외 코드를 식별할 수 있도록 ApiResponse 바디에 실어 전달합니다.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 상황에 맞게 수동 커스텀 응답 처리가 가능하나 기본 BAD_REQUEST 세팅
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        return ApiResponse.of(e.getMessage(), null);
    }
}