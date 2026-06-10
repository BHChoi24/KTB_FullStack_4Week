package com.example.boardlab.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    // 현재는 Spring Security가 없으므로 작성자 식별을 위해 요청으로 받는다.
    @NotNull(message = "user_id_empty")
    private Long userId;

    @NotBlank(message = "content_empty")
    private String content;
}