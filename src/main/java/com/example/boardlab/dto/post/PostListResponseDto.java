package com.example.boardlab.dto.post;

import com.example.boardlab.domain.Post;

/**
 * [클래스 역할] 게시글 목록 조회(GET /posts) 시 전체 목록 화면에 출력할 정보들만 압축하여 전송하는 가방입니다.
 * 원본 Post 도메인에 존재하지 않는 '댓글 수(commentCount)' 필드를 가공해 결합하는 계층적 역할을 수행합니다.
 */
public class PostListResponseDto {
    private Long id;
    private String title;
    private int viewsCount;     // 게시글 도메인에서 가져온 누적 조회수
    private int commentCount;   // 서비스 레이어에서 연산되어 주입된 해당 글의 총 댓글 수

    public PostListResponseDto(Post post, int commentCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.viewsCount = post.getViewsCount();
        this.commentCount = commentCount; // 계산된 댓글 카운트를 화면용 스펙에 바인딩
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public int getViewsCount() { return viewsCount; }
    public int getCommentCount() { return commentCount; }
}