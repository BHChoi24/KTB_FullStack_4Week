package com.example.boardlab.controller;

import com.example.boardlab.domain.Comment;
import com.example.boardlab.dto.comment.CommentRequestDto;
import com.example.boardlab.dto.comment.CommentResponseDto;
import com.example.boardlab.response.ApiResponse;
import com.example.boardlab.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 시트 기준 반영: 명시적 메시지 출력 구조로 전환 및 수정 완료
    @PostMapping("/posts/{postId}/comments")
    public ApiResponse<CommentResponseDto> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        Comment comment = commentService.createComment(postId, requestDto);
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return ApiResponse.of("comment_create_success", responseDto);
    }

    @GetMapping("/comments/{commentId}")
    public ApiResponse<CommentResponseDto> getComment(
            @PathVariable Long commentId
    ) {
        Comment comment = commentService.findById(commentId);
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return ApiResponse.of("comment_access_success", responseDto);
    }

    // 자바 명명 오타 수정 완료 ({commentId} 카멜케이스 바인딩 불일치 교정)
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestParam Long userId
    ) {
        commentService.deleteComment(commentId, userId);
        return ApiResponse.of("comment_delete_success", null);
    }

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