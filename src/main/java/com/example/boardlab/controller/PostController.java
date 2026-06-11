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

/**
 * [클래스 역할] 게시글 관련 HTTP 요청(등록, 전체조회, 상세조회, 수정, 삭제)을 처리하는 컨트롤러입니다.
 */
@RestController
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    /**
     * [게시글 등록 API] POST /posts
     * 제목, 본문, 이미지 등을 받아 게시글을 새롭게 생성합니다. (201 Created 반환)
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    public ApiResponse<PostCreateResponseDto> createPost(
            @Valid @RequestBody PostCreateRequestDto requestDto
    ) {
        Post post = postService.createPost(requestDto);
        return ApiResponse.of("posts_add_success", new PostCreateResponseDto(post));
    }

    /**
     * [게시글 목록 조회 API] GET /posts
     * 저장된 모든 게시글 리스트를 가져오며, 각 게시글에 누적된 댓글 개수를 취합하여 반환합니다.
     */
    @GetMapping("/posts")
    public ApiResponse<List<PostListResponseDto>> getPosts() {
        List<PostListResponseDto> responseDtos = postService.findAll()
                .stream()
                .map(post -> new PostListResponseDto(post, commentService.countByPostId(post.getId())))
                .toList();
        return ApiResponse.of("post_list_success", responseDtos);
    }

    /**
     * [게시글 상세 조회 API] GET /posts/{postId}
     * 특정 게시글의 본문 내용을 가져오고 조회수를 1 증가시킵니다.
     * 하단에 붙은 댓글 리스트 컬렉션까지 함께 묶어서 응답을 포맷팅합니다.
     */
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

    /**
     * [게시글 수정 API] PATCH /posts/{postId}
     * 본인 확인용 유저 식별자(userId)와 수정 내용을 받아 대조 검증 후 수정합니다.
     * 본인이 아닐 경우 시트 명세에 따라 403(forbidden_author) 에러가 발생합니다.
     */
    @PatchMapping("/posts/{postId}")
    public ApiResponse<PostUpdateResponseDto> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequestDto requestDto
    ) {
        Post post = postService.updatePost(postId, requestDto.getUserId(), requestDto);
        return ApiResponse.of("update_success", new PostUpdateResponseDto(post));
    }

    /**
     * [게시글 삭제 API] DELETE /posts/{postId}?userId=값
     * 작성자 본인 검증을 유도하기 위해 Query Parameter로 userId를 수동 접수받습니다.
     * 일치하지 않으면 403 예외가 터지도록 설계되어 있습니다.
     */
    @DeleteMapping("/posts/{postId}")
    public ApiResponse<Void> deletePost(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        postService.deletePost(postId, userId);
        return ApiResponse.of("delete_success", null);
    }
}