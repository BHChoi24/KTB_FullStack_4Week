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
import com.example.boardlab.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 게시글 API의 입구 역할을 하는 Controller입니다.
 * 게시글의 생성/조회/수정/삭제 규칙은 PostService가 처리하고,
 * 사용자와 댓글 정보가 필요할 때 UserService와 CommentService를 사용합니다.
 */
@RestController
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    public PostController(PostService postService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    /**
     * 게시글 작성 API: POST /posts
     * 헤더에서는 작성자 번호를 받고, body에서는 제목/본문/이미지를 받습니다.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    public ApiResponse<PostCreateResponseDto> createPost(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId,
            @Valid @RequestBody PostCreateRequestDto request
    ) {
        // Service가 로그인 여부를 확인하고 게시글을 저장합니다.
        Post createdPost = postService.createPost(userId, request);

        // 저장된 Post를 외부에 보여 줄 응답 DTO로 변환합니다.
        return ApiResponse.of(
                "posts_add_success",
                new PostCreateResponseDto(createdPost)
        );
    }

    /**
     * 게시글 목록 API: GET /posts?page=1
     * @RequestParam은 URL 뒤의 page 값을 받으며, 생략하면 1을 사용합니다.
     */
    @GetMapping("/posts")
    public ApiResponse<List<PostListResponseDto>> getPosts(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId,
            @RequestParam(defaultValue = "1") int page
    ) {
        // 1. 목록 API는 로그인한 사용자만 접근할 수 있습니다.
        userService.requireAuthenticated(userId);

        // 2. 한 페이지의 게시글을 가져옵니다. 이 과제에서는 한 페이지에 최대 10개입니다.
        List<PostListResponseDto> posts = postService.findPage(page, 10).stream()
                // 3. 각 Post에 작성자 닉네임과 댓글 개수를 더해 목록 응답 DTO로 만듭니다.
                .map(post -> new PostListResponseDto(
                        post,
                        userService.findById(post.getUserId()).getNickname(),
                        commentService.countByPostId(post.getId())
                ))
                .toList();
        return ApiResponse.of("post_list_success", posts);
    }

    /** 게시글 상세 API: GET /posts/{postId} */
    @GetMapping("/posts/{postId}")
    public ApiResponse<PostDetailResponseDto> getPost(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId,
            @PathVariable Long postId
    ) {
        // 1. 로그인 여부를 확인합니다.
        userService.requireAuthenticated(userId);

        // 2. 게시글을 조회하면서 조회수를 1 증가시킵니다.
        Post post = postService.findByIdAndIncreaseView(postId);

        // 3. 해당 게시글의 댓글과 각 댓글 작성자의 닉네임을 응답 DTO로 변환합니다.
        List<CommentResponseDto> comments = commentService.findByPostId(postId).stream()
                .map(comment -> new CommentResponseDto(
                        comment,
                        userService.findById(comment.getUserId()).getNickname()
                ))
                .toList();

        // 4. 게시글 정보와 댓글 목록을 하나의 상세 응답으로 묶습니다.
        return ApiResponse.of(
                "posts_access_success",
                new PostDetailResponseDto(
                        post,
                        userService.findById(post.getUserId()).getNickname(),
                        comments
                )
        );
    }

    /**
     * 게시글 수정 API: PATCH /posts/{postId}
     * PATCH이므로 body에서 전달한 필드만 변경합니다.
     */
    @PatchMapping("/posts/{postId}")
    public ApiResponse<PostUpdateResponseDto> updatePost(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId,
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto request
    ) {
        // Service가 로그인, 게시글 존재 여부, 작성자 권한, 빈 문자열을 차례로 검사합니다.
        Post post = postService.updatePost(postId, userId, request);
        return ApiResponse.of("update_success", new PostUpdateResponseDto(post));
    }

    /** 게시글 삭제 API: DELETE /posts/{postId} */
    @DeleteMapping("/posts/{postId}")
    public ApiResponse<Void> deletePost(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId,
            @PathVariable Long postId
    ) {
        // 작성자 확인 후 게시글과 그 아래 댓글을 함께 삭제합니다.
        postService.deletePost(postId, userId);
        return ApiResponse.of("delete_success", null);
    }
}
