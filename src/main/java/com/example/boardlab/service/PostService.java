package com.example.boardlab.service;

import com.example.boardlab.domain.Post;
import com.example.boardlab.dto.post.PostCreateRequestDto;
import com.example.boardlab.dto.post.PostUpdateRequestDto;
import com.example.boardlab.exception.NotFoundException;
import com.example.boardlab.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(PostCreateRequestDto requestDto) {
        Post post = new Post(null, requestDto.getUserId(), requestDto.getTitle(), requestDto.getContent(), requestDto.getImageUrl());
        return postRepository.save(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("posts_not_found"));
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findByIdAndIncreaseView(Long postId) {
        Post post = findById(postId);
        post.increaseViewsCount();
        return post;
    }

    // 현업 레벨 디테일: 작성자 ID(requestUserId) 검증부 수동 설계
    public Post updatePost(Long postId, Long requestUserId, PostUpdateRequestDto requestDto) {
        Post post = findById(postId);

        if (!post.getUserId().equals(requestUserId)) {
            throw new IllegalArgumentException("forbidden_author");
        }

        post.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getImageUrl());
        return post;
    }

    public void deletePost(Long postId, Long requestUserId) {
        Post post = findById(postId);

        if (!post.getUserId().equals(requestUserId)) {
            throw new IllegalArgumentException("forbidden_author");
        }

        postRepository.delete(post);
    }
}