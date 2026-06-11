package com.example.boardlab.domain;

/**
 * [클래스 역할] 댓글(Comment)의 원본 데이터와 데이터 변경 규칙을 관리하는 도메인 모델입니다.
 */
public class Comment {
    private Long id;          // 댓글의 고유 식별 번호
    private Long postId;      // 댓글이 작성된 대상 게시글의 고유 식별자 (외래키 역할)
    private Long userId;      // 댓글을 작성한 회원의 고유 식별자 (수정/삭제 권한 검증 시 활용)
    private String content;    // 댓글 내용 문자열

    public Comment(Long id, Long postId, Long userId, String content) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }

    /**
     * [도메인 비즈니스 로직] 댓글 수정 요청 시 내용(content)을 변경하는 행위입니다.
     */
    public void update(String content) {
        this.content = content;
    }

    // [Getter 메서드] 데이터 전송 및 가공 시 사용됩니다.
    public Long getId() { return id; }
    public Long getPostId() { return postId; }
    public Long getUserId() { return userId; }
    public String getContent() { return content; }
}