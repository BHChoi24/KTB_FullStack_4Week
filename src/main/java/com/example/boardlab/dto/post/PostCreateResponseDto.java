package com.example.boardlab.dto.post;

import com.example.boardlab.domain.Post;

/**
 * [클래스 역할] 게시글 등록 성공 후 발급된 게시글 고유 ID를 응답하기 위한 가방입니다.
 */
public class PostCreateResponseDto {
    private Long id;

    public PostCreateResponseDto(Post post) {
        this.id = post.getId();
    }

    public Long getId() { return id; }
}