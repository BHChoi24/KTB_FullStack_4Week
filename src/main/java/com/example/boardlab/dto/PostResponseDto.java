package com.example.boardlab.dto;

import com.example.boardlab.domain.Post;
import lombok.Getter;
import java.time.LocalDateTime;

/*
 * 게시글 응답을 위한 DTO
 */
@Getter
public class PostResponseDto {

    private Long id;
    private Long userId;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    /*
     * Post 객체를 응답 DTO로 변환하는 생성자
     */
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.userId = post.getUserId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }
}