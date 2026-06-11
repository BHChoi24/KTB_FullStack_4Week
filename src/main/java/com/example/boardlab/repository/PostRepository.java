package com.example.boardlab.repository;

import com.example.boardlab.domain.Post;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** DB 대신 List에 게시글 데이터를 저장하는 메모리 저장소입니다. */
@Repository
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();
    private Long sequence = 1L;

    /** 서버가 시작될 때 목록과 상세 조회에 사용할 기본 게시글을 준비합니다. */
    @PostConstruct
    public void initDummyData() {
        posts.add(new Post(sequence++, 1L, "첫 번째 게시글 제목", "테스트 본문 내용입니다.", "image1.jpg"));
        posts.add(new Post(sequence++, 1L, "두 번째 개발 일지", "Spring Boot 과제 진행 중.", null));
        posts.add(new Post(sequence++, 2L, "질문 있습니다.", "Postman 설정이 안 됩니다.", null));
    }

    public Post save(Post post) {
        // 요청 객체에는 ID가 없으므로 저장할 때 새 ID를 부여합니다.
        Post savedPost = new Post(sequence++, post.getUserId(), post.getTitle(), post.getContent(), post.getImageUrl());
        posts.add(savedPost);
        return savedPost;
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    public List<Post> findByUserId(Long userId) {
        return posts.stream().filter(post -> post.getUserId().equals(userId)).toList();
    }

    public Optional<Post> findById(Long id) {
        return posts.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public void delete(Post post) {
        posts.remove(post);
    }

    public void deleteAllByUserId(Long userId) {
        posts.removeIf(post -> post.getUserId().equals(userId));
    }
}
