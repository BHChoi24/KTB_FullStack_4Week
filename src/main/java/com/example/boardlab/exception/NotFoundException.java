package com.example.boardlab.exception;









//요청한 데이터를 찾을수없을때 예외처리 -> 404 던지기
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}