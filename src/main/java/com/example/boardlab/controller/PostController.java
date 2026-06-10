package com.example.boardlab.controller;

import com.example.boardlab.domain.Post;
import com.example.boardlab.dto.PostRequestDto;
import com.example.boardlab.dto.PostResponseDto;
import com.example.boardlab.response.ApiResponse;
import com.example.boardlab.service.PostService;


//import org.springframework.web.bind.annotation.* 도 되지만 어떤거 새용했는지 한번 보기
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/*
 * 게시글 API 요청 처리 컨트롤러
 */
@RestController
public class PostController {

    private final PostService postService;

    //생성자를 통해 PostService를 주입받는다.
    public PostController(PostService postService) {
        this.postService = postService;
    }


    /*
     * 게시글 작성 API
     * POST /posts
     */
    @PostMapping("/posts")
    public ApiResponse<PostResponseDto> createPost(
            @RequestBody PostRequestDto requestDto
    ) {

        Post post = postService.createPost(requestDto);

        PostResponseDto responseDto = new PostResponseDto(post);

        return ApiResponse.success(responseDto);
    }

    /*
     * 게시글 단건 조회 API
     * GET /posts/{postId}
     */
    @GetMapping("/posts/{postId}")
    public ApiResponse<PostResponseDto> getPost(
            @PathVariable Long postId
    ) {

        Post post = postService.findById(postId);

        PostResponseDto responseDto = new PostResponseDto(post);

        return ApiResponse.success(responseDto);
    }
    /*
     * 게시글 수정 API
     * PUT /posts/{postId}
     */
    @PutMapping("/posts/{postId}")
    public ApiResponse<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto requestDto
    ) {

        Post post = postService.updatePost(postId, requestDto);

        PostResponseDto responseDto = new PostResponseDto(post);

        return ApiResponse.success(responseDto);
    }

    /*
     * 게시글 삭제 API
     * DELETE /posts/{postId}
     */
    @DeleteMapping("/posts/{postId}")
    public ApiResponse<Void> deletePost(
            @PathVariable Long postId
    ) {

        postService.deletePost(postId);

        return ApiResponse.success(null);
    }

    /*
     * 게시글 목록(전체) 조회 API
     * GET /posts
     */
    @GetMapping("/posts")
    public ApiResponse<List<PostResponseDto>> getPosts() {

        List<Post> posts = postService.findAll();

        //List<Post> -> List<PostResponseDto>
        //게시글 객체 여러 개를 응답 DTO 여러 개로 바꾸는 코드
        List<PostResponseDto> responseDtos = posts.stream()
                .map(PostResponseDto::new)//형태 변환
                .toList();

        return ApiResponse.success(responseDtos);
    }



}