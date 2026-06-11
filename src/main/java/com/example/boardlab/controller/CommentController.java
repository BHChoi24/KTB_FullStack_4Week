package com.example.boardlab.controller;

import com.example.boardlab.domain.Comment;
import com.example.boardlab.dto.comment.CommentIdResponseDto;
import com.example.boardlab.dto.comment.CommentRequestDto;
import com.example.boardlab.dto.comment.CommentResponseDto;
import com.example.boardlab.response.ApiResponse;
import com.example.boardlab.service.CommentService;
import com.example.boardlab.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 댓글 작성, 수정, 삭제 요청을 받는 Controller입니다.
 * 댓글의 존재 여부와 작성자 권한 같은 규칙은 CommentService가 담당합니다.
 */
@RestController
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    /**
     * 댓글 작성 API: POST /posts/{postId}/comments
     * 새 댓글이 생성되므로 201 Created를 반환합니다.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts/{postId}/comments")
    public ApiResponse<CommentResponseDto> createComment(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto request
    ) {
        // 1. Service가 로그인 여부와 부모 게시글 존재 여부를 확인하고 댓글을 저장합니다.
        Comment comment = commentService.createComment(postId, userId, request);

        // 2. 응답 명세에 nickname이 필요하므로 사용자 정보를 조회해 DTO에 넣습니다.
        return ApiResponse.of(
                "comment_create_success",
                new CommentResponseDto(comment, userService.findById(userId).getNickname())
        );
    }

    /** 댓글 수정 API: PATCH /posts/{postId}/comments/{commentId} */
    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public ApiResponse<CommentIdResponseDto> updateComment(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto request
    ) {
        // URL의 postId와 commentId 관계까지 확인하여 엉뚱한 게시글 경로로 수정하지 못하게 합니다.
        Comment comment = commentService.updateComment(postId, commentId, userId, request);

        // 수정 응답은 명세에 따라 댓글 번호만 반환합니다.
        return ApiResponse.of("comment_update_success", new CommentIdResponseDto(comment.getId()));
    }

    /** 댓글 삭제 API: DELETE /posts/{postId}/comments/{commentId} */
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ApiResponse<Void> deleteComment(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId,
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        // 로그인, 댓글 경로, 작성자 권한 검사를 통과한 경우에만 삭제됩니다.
        commentService.deleteComment(postId, commentId, userId);
        return ApiResponse.of("comment_delete_success", null);
    }
}
