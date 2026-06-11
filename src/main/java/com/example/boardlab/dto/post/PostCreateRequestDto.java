package com.example.boardlab.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequestDto {

    // 작성자 번호는 body가 아니라 X-USER-ID 헤더에서 받습니다.
    @NotBlank(message = "title_empty")
    private String title;

    @NotBlank(message = "content_empty")
    private String content;

    private String imageUrl;
}
