package com.example.boardlab.dto.post;

import com.example.boardlab.domain.Post;
import com.example.boardlab.dto.CommentResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class PostDetailResponseDto {

    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("user_id")
    private Long userId;

    private String nickname;
    private String title;
    private String content;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("likes_count")
    private int likesCount;

    @JsonProperty("comments_count")
    private int commentsCount;

    @JsonProperty("views_count")
    private int viewsCount;

    private List<CommentResponseDto> comments;

    public PostDetailResponseDto(Post post, List<CommentResponseDto> comments) {
        this.postId = post.getId();
        this.userId = post.getUserId();
        this.nickname = "user" + post.getUserId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();
        this.createdAt = post.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.likesCount = post.getLikesCount();
        this.commentsCount = comments.size();
        this.viewsCount = post.getViewsCount();
        this.comments = comments;
    }
}