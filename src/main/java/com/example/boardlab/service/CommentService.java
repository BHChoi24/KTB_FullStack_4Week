package com.example.boardlab.service;

import com.example.boardlab.domain.Comment;
import com.example.boardlab.dto.comment.CommentRequestDto;
import com.example.boardlab.exception.NotFoundException;
import com.example.boardlab.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(Long postId, CommentRequestDto requestDto) {
        Comment comment = new Comment(null, postId, requestDto.getUserId(), requestDto.getContent());
        return commentRepository.save(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("comment_not_found"));
    }

    public void deleteComment(Long commentId, Long requestUserId) {
        Comment comment = findById(commentId);

        if (!comment.getUserId().equals(requestUserId)) {
            throw new IllegalArgumentException("forbidden_author");
        }

        commentRepository.delete(comment);
    }

    public Comment updateComment(Long commentId, Long requestUserId, CommentRequestDto requestDto) {
        Comment comment = findById(commentId);

        if (!comment.getUserId().equals(requestUserId)) {
            throw new IllegalArgumentException("forbidden_author");
        }

        comment.update(requestDto.getContent());
        return comment;
    }

    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public int countByPostId(Long postId) {
        return findByPostId(postId).size();
    }
}