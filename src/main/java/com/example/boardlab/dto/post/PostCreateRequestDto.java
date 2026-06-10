package com.example.boardlab.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequestDto {

    private Long userId;

    private String title;

    private String content;

    @JsonProperty("image_url")
    private String imageUrl;
}