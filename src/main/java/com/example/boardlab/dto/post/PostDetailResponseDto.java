package com.example.boardlab.dto.post;

import com.example.boardlab.domain.Post;
import com.example.boardlab.dto.comment.CommentResponseDto;

import java.time.LocalDateTime;
import java.util.List;

/** 게시글 본문과 댓글을 한 번에 내려주는 상세 응답 DTO입니다. */
public class PostDetailResponseDto {
    private final Long postId;
    private final String title;
    private final String content;
    private final Long userId;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final String imageUrl;
    private final int likesCount;
    private final int commentsCount;
    private final int viewsCount;
    private final List<CommentResponseDto> comments;

    public PostDetailResponseDto(Post post, String nickname, List<CommentResponseDto> comments) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userId = post.getUserId();
        this.nickname = nickname;
        this.createdAt = post.getCreatedAt();
        this.imageUrl = post.getImageUrl();
        this.likesCount = post.getLikesCount();
        this.commentsCount = comments.size();
        this.viewsCount = post.getViewsCount();
        this.comments = comments;
    }

    public Long getPostId() { return postId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Long getUserId() { return userId; }
    public String getNickname() { return nickname; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getImageUrl() { return imageUrl; }
    public int getLikesCount() { return likesCount; }
    public int getCommentsCount() { return commentsCount; }
    public int getViewsCount() { return viewsCount; }
    public List<CommentResponseDto> getComments() { return comments; }
}
