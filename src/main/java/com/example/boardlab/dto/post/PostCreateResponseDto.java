package com.example.boardlab.dto.post;

import com.example.boardlab.domain.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class PostCreateResponseDto {

    @JsonProperty("post_id")
    private Long postId;

    private String title;

    private String content;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("created_at")
    private String createdAt;

    public PostCreateResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();
        this.createdAt = post.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}