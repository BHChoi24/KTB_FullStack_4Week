package com.example.boardlab.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * [클래스 역할] 게시글 수정(PATCH /posts/{postId}) 요청 시 클라이언트가 제공하는 필드를 담는 가방입니다.
 */
public class PostUpdateRequestDto {
    @NotNull(message = "user_id_empty")
    private Long userId; // 실제 현업 레벨의 권한 검증(403)을 우회 재현하기 위해 몸체에 실어 보내는 요청자 본인 ID

    @NotBlank(message = "title_empty")
    private String title;

    @NotBlank(message = "content_empty")
    private String content;

    private String imageUrl;

    public Long getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
}