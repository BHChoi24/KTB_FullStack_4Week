package com.example.boardlab.dto.post;

import com.example.boardlab.domain.Post;

import java.time.LocalDateTime;

/** 목록 화면에 필요한 값만 모은 응답 DTO입니다. */
public class PostListResponseDto {
    private final Long postId;
    private final String title;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final int likesCount;
    private final int commentsCount;
    private final int viewsCount;

    public PostListResponseDto(Post post, String nickname, int commentsCount) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.nickname = nickname;
        this.createdAt = post.getCreatedAt();
        this.likesCount = post.getLikesCount();
        this.commentsCount = commentsCount;
        this.viewsCount = post.getViewsCount();
    }

    public Long getPostId() { return postId; }
    public String getTitle() { return title; }
    public String getNickname() { return nickname; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public int getLikesCount() { return likesCount; }
    public int getCommentsCount() { return commentsCount; }
    public int getViewsCount() { return viewsCount; }
}
