package com.example.boardlab.controller;

import com.example.boardlab.domain.Post;
import com.example.boardlab.dto.comment.CommentResponseDto;
import com.example.boardlab.dto.post.*;
import com.example.boardlab.response.ApiResponse;
import com.example.boardlab.service.CommentService;
import com.example.boardlab.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    public ApiResponse<PostCreateResponseDto> createPost(
            @Valid @RequestBody PostCreateRequestDto requestDto
    ) {
        Post post = postService.createPost(requestDto);
        return ApiResponse.of("posts_add_success", new PostCreateResponseDto(post));
    }

    @GetMapping("/posts")
    public ApiResponse<List<PostListResponseDto>> getPosts() {
        List<PostListResponseDto> responseDtos = postService.findAll()
                .stream()
                .map(post -> new PostListResponseDto(post, commentService.countByPostId(post.getId())))
                .toList();
        return ApiResponse.of("post_list_success", responseDtos);
    }

    @GetMapping("/posts/{postId}")
    public ApiResponse<PostDetailResponseDto> getPost(
            @PathVariable Long postId
    ) {
        Post post = postService.findByIdAndIncreaseView(postId);
        List<CommentResponseDto> comments = commentService.findByPostId(postId)
                .stream()
                .map(CommentResponseDto::new)
                .toList();
        return ApiResponse.of("posts_access_success", new PostDetailResponseDto(post, comments));
    }

    // 수정자 검증을 위해 본문 내부나 커스텀 인증 변수가 원래 필요하나, 임시로 DTO의 userId를 사용하여 대조 검증 처리
    @PatchMapping("/posts/{postId}")
    public ApiResponse<PostUpdateResponseDto> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequestDto requestDto
    ) {
        // 실제 현업 환경 구조를 감안하여 로직 변경 호출
        Post post = postService.updatePost(postId, requestDto.getUserId(), requestDto);
        return ApiResponse.of("update_success", new PostUpdateResponseDto(post));
    }

    // 삭제 시에도 명세서 대조용 예외 처리를 재현하기 위해 쿼리 파라미터나 헤더 등으로 userId를 받는 구조 필요 (임시 QueryParam 처리)
    @DeleteMapping("/posts/{postId}")
    public ApiResponse<Void> deletePost(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        postService.deletePost(postId, userId);
        return ApiResponse.of("delete_success", null);
    }
}