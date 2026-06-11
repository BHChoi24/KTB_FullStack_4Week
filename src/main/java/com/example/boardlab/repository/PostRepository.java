package com.example.boardlab.repository;

import com.example.boardlab.domain.Post;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * [클래스 역할] 게시글 메모리 컬렉션 생명주기를 주관하는 인메모리 포스트 저장소입니다.
 */
@Repository
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();
    private Long sequence = 1L;

    /**
     * [더미 데이터 적재] 사전에 글이 등록되어 있는 상태를 테스트하기 위해 임의의 3개 게시글을 기포팅합니다.
     */
    @PostConstruct
    public void initDummyData() {
        posts.add(new Post(sequence++, 1L, "첫 번째 게시글 제목", "테스트 본문 내용입니다.", "image1.jpg"));
        posts.add(new Post(sequence++, 1L, "두 번째 개발 일지", "Spring Boot 과제 진행 중.", null));
        posts.add(new Post(sequence++, 2L, "질문 있습니다.", "Postman 설정이 안 됩니다.", null));
    }

    public Post save(Post post) {
        Post savedPost = new Post(sequence++, post.getUserId(), post.getTitle(), post.getContent(), post.getImageUrl());
        posts.add(savedPost);
        return savedPost;
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    public Optional<Post> findById(Long id) {
        return posts.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public void delete(Post post) {
        posts.remove(post);
    }
}