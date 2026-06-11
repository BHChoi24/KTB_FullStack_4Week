package com.example.boardlab.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * [클래스 역할] 댓글 작성 및 수정 시 본문 내용과 요청을 시도하는 사용자의 식별값을 전송받아 검증하는 가방입니다.
 */
public class CommentRequestDto {
    @NotNull(message = "user_id_empty")
    private Long userId; // 댓글 수정/삭제 시 403 예외 상황(본인 검증 실패)을 유도하기 위해 필수 수집하는 ID

    @NotBlank(message = "content_empty")
    private String content;

    public Long getUserId() { return userId; }
    public String getContent() { return content; }
}