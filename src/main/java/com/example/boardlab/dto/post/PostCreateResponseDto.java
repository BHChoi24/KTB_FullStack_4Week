package com.example.boardlab.dto.post;

import com.example.boardlab.domain.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PostCreateResponseDto {

    @JsonProperty("post_id")
    private Long postId;

    public PostCreateResponseDto(Post post) {
        this.postId = post.getId();
    }
}
