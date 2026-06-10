package com.example.boardlab.exception;

import com.example.boardlab.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



//프로젝트 전체에서 발생하는 예외를 공통으로 처리하는 클래스
//@RestControllerAdvice -> 전체 Controller에서 발생한 예외를 한 곳에서 처리하게 해줌
@RestControllerAdvice
public class GlobalExceptionHandler {

    //NotFoundException이 발생하면 404 Not Found로 응답한다.
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<Void> handleNotFoundException(
            NotFoundException exception
    ) {
        return new ApiResponse<>(exception.getMessage(), null);
    }
}