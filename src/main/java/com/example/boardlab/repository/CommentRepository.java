package com.example.boardlab.repository;

import com.example.boardlab.domain.Comment;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/** DB 대신 List에 댓글 데이터를 저장하는 메모리 저장소입니다. */
@Repository
public class CommentRepository {
    private final List<Comment> comments = new ArrayList<>();
    private Long sequence = 1L;

    /** 서버가 시작될 때 게시글 상세 조회에 사용할 기본 댓글을 준비합니다. */
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

    public List<Comment> findByPostId(Long postId) {
        return comments.stream()
                .filter(c -> c.getPostId().equals(postId))
                .toList();
    }

    public Optional<Comment> findById(Long id) {
        return comments.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public void delete(Comment comment) {
        comments.remove(comment);
    }

    public void deleteAllByPostId(Long postId) {
        comments.removeIf(comment -> comment.getPostId().equals(postId));
    }

    public void deleteAllByUserId(Long userId) {
        comments.removeIf(comment -> comment.getUserId().equals(userId));
    }
}
