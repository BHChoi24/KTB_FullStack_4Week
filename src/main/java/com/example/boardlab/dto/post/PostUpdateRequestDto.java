package com.example.boardlab.dto.post;

/** PATCH 요청이므로 null인 필드는 기존 값을 유지합니다. */
public class PostUpdateRequestDto {
    private String title;
    private String content;
    private String imageUrl;

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
}
