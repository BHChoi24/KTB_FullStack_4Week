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

/*
 * 프로젝트 전체에서 발생하는 예외를 공통으로 처리하는 클래스
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * 요청한 데이터를 찾을 수 없을 때 404 응답을 반환한다.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<Void> handleNotFoundException(
            NotFoundException exception
    ) {
        return ApiResponse.of(exception.getMessage(), null);
    }

    /*
     * @Valid 검증 실패 시 400 응답을 반환한다.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(
            MethodArgumentNotValidException exception
    ) {

        List<ValidationError> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationError(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        return new ErrorResponse(
                "invalid_input_value",
                null,
                errors
        );
    }
}