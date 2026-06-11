package com.example.boardlab.exception;

import com.example.boardlab.response.ApiResponse;
import com.example.boardlab.response.ErrorResponse;
import com.example.boardlab.response.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 애플리케이션에서 발생한 예외를 HTTP 응답으로 바꾸는 공통 처리 클래스입니다.
 *
 * 예외 처리 흐름:
 * 1. Controller가 Service 메서드를 호출합니다.
 * 2. Service가 잘못된 상황을 발견하면 throw new ...Exception()을 실행합니다.
 * 3. throw가 실행되면 Service와 Controller의 나머지 코드는 실행되지 않습니다.
 * 4. @RestControllerAdvice가 붙은 이 클래스가 예외를 가로챕니다.
 * 5. 예외 종류에 맞는 @ExceptionHandler 메서드가 상태 코드와 JSON을 반환합니다.
 *
 * 이렇게 한곳에서 처리하면 각 Controller에 try-catch를 반복해서 작성하지 않아도 됩니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * NotFoundException 전용 처리 메서드입니다.
     * @ResponseStatus가 실제 HTTP 상태를 404로 설정합니다.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<Void> handleNotFoundException(NotFoundException e) {
        // 예외 생성 시 저장한 메시지를 그대로 API 응답에 사용합니다.
        return ApiResponse.of(e.getMessage(), null);
    }

    /**
     * DTO의 @Valid 검증 실패를 처리합니다.
     * Spring이 MethodArgumentNotValidException을 자동으로 발생시킵니다.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        // 한 요청에서 여러 필드가 잘못될 수 있으므로 모든 필드 오류를 List로 변환합니다.
        List<ValidationError> validationErrors = e.getBindingResult().getFieldErrors().stream()
                // error.getField(): 잘못된 필드명, getDefaultMessage(): DTO 어노테이션의 message
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                .toList();

        return new ErrorResponse("invalid_input_value", validationErrors);
    }

    /**
     * Service에서 발견한 중복 이메일, 비밀번호 불일치 등을 400으로 처리합니다.
     * 위 메서드는 어노테이션 검증용이고, 이 메서드는 직접 작성한 검증용입니다.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidInputException.class)
    public ErrorResponse handleInvalidInputException(InvalidInputException e) {
        return new ErrorResponse("invalid_input_value", e.getErrors());
    }

    /** 잘못된 page 요청을 400으로 처리합니다. */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidPageException.class)
    public ApiResponse<Void> handleInvalidPageException(InvalidPageException e) {
        return ApiResponse.of(e.getMessage(), null);
    }

    /** 로그인 실패나 인증 헤더 누락을 401로 처리합니다. */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ApiResponse<Void> handleAuthenticationException(AuthenticationException e) {
        return ApiResponse.of(e.getMessage(), null);
    }

    /** 다른 사용자의 글이나 댓글을 변경하려는 요청을 403으로 처리합니다. */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ApiResponse<Void> handleForbiddenException(ForbiddenException e) {
        return ApiResponse.of(e.getMessage(), null);
    }

    /**
     * 위에서 예상하지 못한 나머지 모든 예외를 처리하는 마지막 안전망입니다.
     * Exception.class는 범위가 넓으므로 반드시 구체적인 예외 처리 메서드보다 아래에 둡니다.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(Exception e) {
        // 서버 내부 오류 내용은 보안상 클라이언트에게 노출하지 않습니다.
        // ResponseEntity를 사용해 상태 코드 500과 응답 body를 함께 만듭니다.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.of("internal_server_error", null));
    }
}
