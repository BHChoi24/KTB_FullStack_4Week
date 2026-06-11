package com.example.boardlab.service;

import com.example.boardlab.domain.Post;
import com.example.boardlab.dto.post.PostCreateRequestDto;
import com.example.boardlab.dto.post.PostUpdateRequestDto;
import com.example.boardlab.exception.NotFoundException;
import com.example.boardlab.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * [클래스 역할] 게시글 조작에 대한 권한 제어 및 가공 로직을 수행하는 서비스 클래스입니다.
 */
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * 신규 게시글을 임시 도메인으로 전환하여 저장소 인프라에 누적시킵니다.
     */
    public Post createPost(PostCreateRequestDto requestDto) {
        Post post = new Post(null, requestDto.getUserId(), requestDto.getTitle(), requestDto.getContent(), requestDto.getImageUrl());
        return postRepository.save(post);
    }

    /**
     * 게시글 단건 검색 가용성 확인 로직 (실패 시 posts_not_found 반환)
     */
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("posts_not_found"));
    }

    /**
     * 전체 게시글 원본 데이터셋 출력 요청 위임
     */
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    /**
     * 게시글을 단건 호출하는 순간 도메인 엔티티 내부 계수기(조회수)를 수동 가산시킵니다.
     */
    public Post findByIdAndIncreaseView(Long postId) {
        Post post = findById(postId);
        post.increaseViewsCount();
        return post;
    }

    /**
     * 현업 디테일 반영: 기존 저장되어 있던 게시글 객체의 작성자ID와 수정 요청 사용자 ID를 수동 대조합니다.
     * 불법 접근(탈취 시도)일 경우 'forbidden_author' 예외를 던집니다.
     */
    public Post updatePost(Long postId, Long requestUserId, PostUpdateRequestDto requestDto) {
        Post post = findById(postId);

        if (!post.getUserId().equals(requestUserId)) {
            throw new IllegalArgumentException("forbidden_author");
        }

        post.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getImageUrl());
        return post;
    }

    /**
     * 게시글 수동 파괴 전 권한 검증을 처리합니다.
     */
    public void deletePost(Long postId, Long requestUserId) {
        Post post = findById(postId);

        if (!post.getUserId().equals(requestUserId)) {
            throw new IllegalArgumentException("forbidden_author");
        }

        postRepository.delete(post);
    }
}