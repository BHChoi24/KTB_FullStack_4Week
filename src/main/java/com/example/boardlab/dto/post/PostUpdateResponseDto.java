package com.example.boardlab.dto.post;

import com.example.boardlab.domain.Post;

/**
 * [클래스 역할] 게시글 수정이 안전하게 완료된 후 타깃 게시글 ID 번호를 리턴해 주기 위한 응답 가방입니다.
 */
public class PostUpdateResponseDto {
    private Long id;

    public PostUpdateResponseDto(Post post) {
        this.id = post.getId();
    }

    public Long getId() { return id; }
}