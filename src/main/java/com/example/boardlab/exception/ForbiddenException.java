package com.example.boardlab.exception;

/**
 * 인증된 사용자에게 해당 작업의 권한이 없을 때 발생시키는 예외입니다.
 *
 * 401과 403의 차이:
 * - 401: 로그인한 사용자인지 확인할 수 없음
 * - 403: 로그인은 확인했지만 다른 사람의 글이나 댓글에 접근함
 *
 * GlobalExceptionHandler가 이 예외를 HTTP 403 Forbidden으로 변환합니다.
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
