package com.example.boardlab.service;

import com.example.boardlab.domain.Post;
import com.example.boardlab.dto.post.PostCreateRequestDto;
import com.example.boardlab.dto.post.PostUpdateRequestDto;
import com.example.boardlab.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


//게시글(비즈니스로직)
@Service
public class PostService {

    // DB 대신 사용할 게시글 임시 저장소
    private final List<Post> posts = new ArrayList<>();

    private Long sequence = 1L;

    // 게시글 작성
    public Post createPost(PostCreateRequestDto requestDto) {
        Post post = new Post(
                sequence++,
                requestDto.getUserId(),
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getImageUrl()
        );

        posts.add(post);
        return post;
    }

    // 게시글 단건 조회
    public Post findById(Long postId) {
        for (Post post : posts) {
            if (post.getId().equals(postId)) {
                return post;
            }
        }

        throw new NotFoundException("post_not_found");
    }

    // 게시글 목록 조회
    public List<Post> findAll() {
        return posts;
    }

    // 게시글 상세 조회 시 조회수 증가
    public Post findByIdAndIncreaseView(Long postId) {
        Post post = findById(postId);
        post.increaseViewsCount();
        return post;
    }

    // 게시글 수정
    public Post updatePost(Long postId, PostUpdateRequestDto requestDto) {
        Post post = findById(postId);

        post.update(
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getImageUrl()
        );

        return post;
    }

    // 게시글 삭제
    public void deletePost(Long postId) {
        Post post = findById(postId);
        posts.remove(post);
    }
}