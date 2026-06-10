package com.example.boardlab.controller;

import com.example.boardlab.domain.Comment;
import com.example.boardlab.dto.comment.CommentRequestDto;
import com.example.boardlab.dto.comment.CommentResponseDto;
import com.example.boardlab.response.ApiResponse;
import com.example.boardlab.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


//댓글 API 요청을 처리하는 컨트롤러
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성 API
    // POST /posts/{postId}/comments
    @PostMapping("/posts/{postId}/comments")
    public ApiResponse<CommentResponseDto> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {

        Comment comment = commentService.createComment(postId, requestDto);

        CommentResponseDto responseDto = new CommentResponseDto(comment);

        return ApiResponse.success(responseDto);
    }

    // 댓글 단건 조회 API
    // GET /comments/{commentId}
    @GetMapping("/comments/{commentId}")
    public ApiResponse<CommentResponseDto> getComment(
            @PathVariable Long commentId
    ) {

        Comment comment = commentService.findById(commentId);

        CommentResponseDto responseDto = new CommentResponseDto(comment);

        return ApiResponse.success(responseDto);
    }

    // 댓글 삭제 API
    // DELETE /comments/{commentId}
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {

        commentService.deleteComment(commentId);

        return ApiResponse.success(null);
    }

    // 댓글 수정 API
    // PATCH /posts/{postId}/comments/{commentId}
    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public ApiResponse<CommentResponseDto> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {

        Comment comment = commentService.updateComment(commentId, requestDto);

        CommentResponseDto responseDto = new CommentResponseDto(comment);

        return ApiResponse.success(responseDto);
    }
}