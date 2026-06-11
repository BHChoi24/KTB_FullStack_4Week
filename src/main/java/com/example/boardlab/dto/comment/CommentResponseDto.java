package com.example.boardlab.dto.comment;

import com.example.boardlab.domain.Comment;

/**
 * [클래스 역할] 댓글 정보를 화면이나 게시글 상세 정보 내부에 배열 형태로 출력할 때 규격화된 포맷으로 변환해 주는 응답 가방입니다.
 */
public class CommentResponseDto {
    private Long commentId;  // 시트 명세에 명시된 하위 필드명 준수 ("comment_id" 매핑용)
    private Long userId;     // 댓글을 작성한 사람의 ID
    private String content;   // 댓글 내용

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.userId = comment.getUserId();
        this.content = comment.getContent();
    }

    public Long getCommentId() { return commentId; }
    public Long getUserId() { return userId; }
    public String getContent() { return content; }
}