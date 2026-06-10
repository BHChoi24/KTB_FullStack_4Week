package com.example.boardlab.dto.post;

import com.example.boardlab.domain.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class PostListResponseDto {

    @JsonProperty("post_id")
    private Long postId;

    private String title;

    // 현재는 User 연동을 단순화하기 위해 임시 닉네임 사용
    private String nickname;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("likes_count")
    private int likesCount;

    @JsonProperty("comments_count")
    private int commentsCount;

    @JsonProperty("views_count")
    private int viewsCount;

    public PostListResponseDto(Post post, int commentsCount) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.nickname = "user" + post.getUserId();
        this.createdAt = post.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.likesCount = post.getLikesCount();
        this.commentsCount = commentsCount;
        this.viewsCount = post.getViewsCount();
    }
}