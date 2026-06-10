package com.example.boardlab.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequestDto {

    //유저 널이면 안됌
    @NotNull(message = "user_id_empty")
    @JsonProperty("user_id")
    private Long userId;

    //빈칸알림
    @NotBlank(message = "title_empty")
    private String title;

    @NotBlank(message = "content_empty")
    private String content;

    @JsonProperty("image_url")
    private String imageUrl;
}