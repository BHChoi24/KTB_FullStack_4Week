package com.example.boardlab.dto.comment;

import com.example.boardlab.domain.Comment;

/** 댓글 도메인 객체를 API 응답 형식으로 변환합니다. */
public class CommentResponseDto {
    private Long commentId;  // 시트 명세에 명시된 하위 필드명 준수 ("comment_id" 매핑용)
    private Long userId;     // 댓글을 작성한 사람의 ID
    private String nickname;
    private java.time.LocalDateTime createdAt;
    private String content;   // 댓글 내용

    public CommentResponseDto(Comment comment, String nickname) {
        this.commentId = comment.getId();
        this.userId = comment.getUserId();
        this.nickname = nickname;
        this.createdAt = comment.getCreatedAt();
        this.content = comment.getContent();
    }

    public Long getCommentId() { return commentId; }
    public Long getUserId() { return userId; }
    public String getNickname() { return nickname; }
    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public String getContent() { return content; }
}
