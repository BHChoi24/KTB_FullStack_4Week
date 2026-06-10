package com.example.boardlab.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * post 게시글 생성 요청을 받기 위한 DTO
 */
@Getter
@NoArgsConstructor
public class PostRequestDto {

    private Long userId;
    private String title;
    private String content;
}