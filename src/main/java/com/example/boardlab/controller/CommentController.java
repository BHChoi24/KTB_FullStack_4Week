package com.example.boardlab.controller;

import com.example.boardlab.domain.Comment;
import com.example.boardlab.dto.comment.CommentRequestDto;
import com.example.boardlab.dto.comment.CommentResponseDto;
import com.example.boardlab.response.ApiResponse;
import com.example.boardlab.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * [클래스 역할] 게시글 내부 댓글 관련 HTTP 요청(작성, 상세조회, 수정, 삭제)을 처리하는 컨트롤러입니다.
 */
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * [댓글 작성 API] POST /posts/{postId}/comments
     * 특정 게시글 번호 하위에 새로운 댓글 문자열을 저장합니다.
     */
    @PostMapping("/posts/{postId}/comments")
    public ApiResponse<CommentResponseDto> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        Comment comment = commentService.createComment(postId, requestDto);
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return ApiResponse.of("comment_create_success", responseDto);
    }

    /**
     * [댓글 단건 조회 API] GET /comments/{commentId}
     * 고유한 댓글 ID 번호를 기반으로 댓글 데이터를 역추적하여 반환합니다.
     */
    @GetMapping("/comments/{commentId}")
    public ApiResponse<CommentResponseDto> getComment(
            @PathVariable Long commentId
    ) {
        Comment comment = commentService.findById(commentId);
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return ApiResponse.of("comment_access_success", responseDto);
    }

    /**
     * [댓글 삭제 API] DELETE /posts/{postId}/comments/{commentId}?userId=값
     * 경로 오타가 카멜케이스({commentId})로 수정되어 정상 파싱됩니다.
     * Query Parameter 파라미터로 본인 ID를 대조하여 403 검증을 수행합니다.
     */
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestParam Long userId
    ) {
        commentService.deleteComment(commentId, userId);
        return ApiResponse.of("comment_delete_success", null);
    }

    /**
     * [댓글 수정 API] PATCH /posts/{postId}/comments/{commentId}
     * 요청 Body에 담긴 DTO 안의 userId 정보를 꺼내 본인 소유의 댓글인지 파악한 뒤 수정 내용을 갱신합니다.
     */
    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public ApiResponse<CommentResponseDto> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        Comment comment = commentService.updateComment(commentId, requestDto.getUserId(), requestDto);
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return ApiResponse.of("comment_update_success", responseDto);
    }
}