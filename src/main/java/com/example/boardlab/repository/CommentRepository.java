package com.example.boardlab.repository;

import com.example.boardlab.domain.Comment;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * [클래스 역할] 댓글 컬렉션 저장 관리를 도맡아 하는 인메모리 댓글 저장소입니다.
 */
@Repository
public class CommentRepository {
    private final List<Comment> comments = new ArrayList<>();
    private Long sequence = 1L;

    /**
     * [더미 데이터 적재] 특정 게시글 번호(postId)에 종속된 더미 댓글 3건을 부팅 시 자동 매핑합니다.
     */
    @PostConstruct
    public void initDummyData() {
        comments.add(new Comment(sequence++, 1L, 2L, "좋은 글이네요! 많이 배워갑니다."));
        comments.add(new Comment(sequence++, 1L, 1L, "감사합니다. 열공하세요."));
        comments.add(new Comment(sequence++, 3L, 1L, "어떤 에러가 발생하시나요?"));
    }

    public Comment save(Comment comment) {
        Comment savedComment = new Comment(sequence++, comment.getPostId(), comment.getUserId(), comment.getContent());
        comments.add(savedComment);
        return savedComment;
    }

    /**
     * 쿼리 튜닝 대용 스트림 파이프라인: 특정 게시글(postId) 하위의 댓글셋만 필터링 수집합니다.
     */
    public List<Comment> findByPostId(Long postId) {
        return comments.stream()
                .filter(c -> c.getPostId().equals(postId))
                .collect(Collectors.toList());
    }

    public Optional<Comment> findById(Long id) {
        return comments.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public void delete(Comment comment) {
        comments.remove(comment);
    }
}