package com.example.boardlab.dto;

import com.example.boardlab.domain.Comment;
import lombok.Getter;
import java.time.LocalDateTime;


/*
 * 댓글 응답을 위한 DTO
 */
@Getter
public class CommentResponseDto {

    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    //Comment 객체를 응답 DTO로 변환하는 생성자
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPostId();
        this.userId = comment.getUserId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}