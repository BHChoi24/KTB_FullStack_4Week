package com.example.boardlab.domain;

import java.time.LocalDateTime;

import lombok.Getter;

/*
 * Post 클래스는 게시글 데이터를 표현 -> 도메인요청 POST전부 여기로 일단
 * DB를 사용않하고 만들어서 Entity를, 일반 Java 클래스로 구현하여 진행
 * 시트 POST /posts, GET /posts/{postId}
 */
@Getter
public class Post {

    private Long id;
    private Long userId;
    private String title;
    private String content;
    // 게시글 작성 시 선택적으로 받을 이미지 URL
    private String imageUrl;
    private LocalDateTime createdAt;
    private int likesCount;
    private int viewsCount;

    //게시글 객체를 생성하기 위한 생성자
    public Post(Long id, Long userId, String title, String content, String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = LocalDateTime.now();
        this.likesCount = 0;
        this.viewsCount = 0;
    }

    // 게시글 수정 기능
    public void update(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    // 상세 조회 시 조회수 증가용
    public void increaseViewsCount() {
        this.viewsCount++;
    }


}

