package com.example.boardlab.domain;

import lombok.Getter;

import java.time.LocalDateTime;

/*
 * 댓글 데이터를 표현하는 클래스
 */
@Getter
public class Comment {

    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    //댓글 객체를 생성하기 위한 생성자
    public Comment(Long id, Long postId, Long userId, String content) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    //댓글 내용을 수정하는 메서드
    public void update(String content) {
        this.content = content;
    }

}