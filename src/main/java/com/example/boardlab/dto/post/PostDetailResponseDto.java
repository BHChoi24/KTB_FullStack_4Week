package com.example.boardlab.dto.post;

import com.example.boardlab.domain.Post;
import com.example.boardlab.dto.comment.CommentResponseDto;
import java.util.List;

/**
 * [클래스 역할] 게시글 상세 보기(GET /posts/{postId}) 호출 시 게시글의 전체 본문과
 * 그 하단에 종속된 댓글 리스트 배열(List)까지 복합적으로 묶어서 하나의 큰 JSON으로 구조화하는 객체입니다.
 */
public class PostDetailResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private int viewsCount;
    private List<CommentResponseDto> comments; // 하단에 노출할 댓글 Response DTO의 집합 배열

    public PostDetailResponseDto(Post post, List<CommentResponseDto> comments) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();
        this.viewsCount = post.getViewsCount();
        this.comments = comments; // 게시글 정보와 댓글 정보의 결합 처리
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public int getViewsCount() { return viewsCount; }
    public List<CommentResponseDto> getComments() { return comments; }
}