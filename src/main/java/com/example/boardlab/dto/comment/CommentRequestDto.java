package com.example.boardlab.dto.comment;

import jakarta.validation.constraints.NotBlank;

/** 댓글 작성과 수정 요청의 content를 검증합니다. */
public class CommentRequestDto {
    @NotBlank(message = "content_empty")
    private String content;

    public String getContent() { return content; }
}
