package com.example.boardlab.repository;

import com.example.boardlab.domain.Comment;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CommentRepository {
    private final List<Comment> comments = new ArrayList<>();
    private Long sequence = 1L;

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
                .collect(Collectors.toList());
    }

    public Optional<Comment> findById(Long id) {
        return comments.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public void delete(Comment comment) {
        comments.remove(comment);
    }
}