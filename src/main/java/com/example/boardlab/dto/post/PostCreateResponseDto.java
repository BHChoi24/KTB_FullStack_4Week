package com.example.boardlab.dto.post;

import com.example.boardlab.domain.Post;

/** 새로 작성된 게시글 정보를 반환합니다. */
public class PostCreateResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String imageUrl;
    private java.time.LocalDateTime createdAt;

    public PostCreateResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();
        this.createdAt = post.getCreatedAt();
    }

    public Long getPostId() { return postId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
}
