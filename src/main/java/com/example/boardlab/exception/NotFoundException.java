package com.example.boardlab.exception;

/**
 * [클래스 역할] 조회 대상 리소스가 인메모리 저장소에 존재하지 않을 때 의도적으로 발생시키는 커스텀 예외 클래스입니다.
 * 일반적인 Java 시스템 런타임 에러 대신 이 예외를 호출하여 GlobalExceptionHandler가 404 상태 코드로 제어하도록 가로챕니다.
 */
public class NotFoundException extends RuntimeException {

    /**
     * 예외 발생 시 생성자 인자로 메세지 규격("posts_not_found", "user_not_found" 등)을 강제 주입받습니다.
     */
    public NotFoundException(String message) {
        super(message);
    }
}