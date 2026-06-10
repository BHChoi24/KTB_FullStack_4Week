package com.example.boardlab.controller;

import com.example.boardlab.domain.Post;
import com.example.boardlab.dto.comment.CommentResponseDto;
import com.example.boardlab.dto.post.PostCreateRequestDto;
import com.example.boardlab.dto.post.PostCreateResponseDto;
import com.example.boardlab.dto.post.PostDetailResponseDto;
import com.example.boardlab.dto.post.PostListResponseDto;
import com.example.boardlab.dto.post.PostUpdateRequestDto;
import com.example.boardlab.dto.post.PostUpdateResponseDto;
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

    // 게시글 작성 API
    // POST /posts
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    public ApiResponse<PostCreateResponseDto> createPost(
            @Valid @RequestBody PostCreateRequestDto requestDto
    ) {
        Post post = postService.createPost(requestDto);

        return ApiResponse.of(
                "posts_add_success",
                new PostCreateResponseDto(post)
        );
    }

    // 게시글 목록 조회 API
    // GET /posts
    @GetMapping("/posts")
    public ApiResponse<List<PostListResponseDto>> getPosts() {
        List<PostListResponseDto> responseDtos = postService.findAll()
                .stream()
                .map(post -> new PostListResponseDto(
                        post,
                        commentService.countByPostId(post.getId())
                ))
                .toList();

        return ApiResponse.of("post_list_success", responseDtos);
    }

    // 게시글 상세 조회 API
    // GET /posts/{postId}
    @GetMapping("/posts/{postId}")
    public ApiResponse<PostDetailResponseDto> getPost(
            @PathVariable Long postId
    ) {
        Post post = postService.findByIdAndIncreaseView(postId);

        List<CommentResponseDto> comments = commentService.findByPostId(postId)
                .stream()
                .map(CommentResponseDto::new)
                .toList();

        return ApiResponse.of(
                "posts_access_success",
                new PostDetailResponseDto(post, comments)
        );
    }

    // 게시글 수정 API
    // PATCH /posts/{postId}
    @PatchMapping("/posts/{postId}")
    public ApiResponse<PostUpdateResponseDto> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequestDto requestDto
    ) {
        Post post = postService.updatePost(postId, requestDto);

        return ApiResponse.of(
                "update_success",
                new PostUpdateResponseDto(post)
        );
    }

    // 게시글 삭제 API
    // DELETE /posts/{postId}
    @DeleteMapping("/posts/{postId}")
    public ApiResponse<Void> deletePost(
            @PathVariable Long postId
    ) {
        postService.deletePost(postId);

        return ApiResponse.of("delete_success", null);
    }
}