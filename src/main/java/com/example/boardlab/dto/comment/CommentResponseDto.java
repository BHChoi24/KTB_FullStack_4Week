package com.example.boardlab.dto.comment;

import com.example.boardlab.domain.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

/*
 * 댓글 응답을 위한 DTO
 *
 * 시트 기준:
 * comment_id, user_id, nickname, created_at, content 형태로 응답한다.
 */
@Getter
public class CommentResponseDto {

    @JsonProperty("comment_id")
    private Long commentId;

    @JsonProperty("user_id")
    private Long userId;

    private String nickname;

    @JsonProperty("created_at")
    private String createdAt;

    private String content;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.userId = comment.getUserId();

        // 현재는 DB/Security가 없으므로 임시 닉네임 생성
        this.nickname = "user" + comment.getUserId();

        this.createdAt = comment.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        this.content = comment.getContent();
    }
}