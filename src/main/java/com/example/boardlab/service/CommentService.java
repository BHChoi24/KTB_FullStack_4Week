package com.example.boardlab.service;

import com.example.boardlab.domain.Comment;
import com.example.boardlab.dto.comment.CommentRequestDto;
import com.example.boardlab.exception.ForbiddenException;
import com.example.boardlab.exception.NotFoundException;
import com.example.boardlab.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    public CommentService(
            CommentRepository commentRepository,
            PostService postService,
            UserService userService
    ) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public Comment createComment(Long postId, Long userId, CommentRequestDto request) {
        // 1. 로그인한 사용자인지 확인합니다.
        userService.requireAuthenticated(userId);

        // 2. 댓글을 작성할 게시글이 실제로 존재하는지 확인합니다.
        postService.findById(postId);

        // 3. 댓글을 저장합니다.
        return commentRepository.save(new Comment(null, postId, userId, request.getContent()));
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("comment_not_found"));
    }

    public void deleteComment(Long postId, Long commentId, Long requestUserId) {
        // 로그인 -> 댓글 경로 확인 -> 작성자 확인 -> 삭제 순서입니다.
        userService.requireAuthenticated(requestUserId);
        Comment comment = findCommentInPost(postId, commentId);
        checkAuthor(comment, requestUserId);
        commentRepository.delete(comment);
    }

    public Comment updateComment(
            Long postId,
            Long commentId,
            Long requestUserId,
            CommentRequestDto request
    ) {
        // 로그인 -> 댓글 경로 확인 -> 작성자 확인 -> 수정 순서입니다.
        userService.requireAuthenticated(requestUserId);
        Comment comment = findCommentInPost(postId, commentId);
        checkAuthor(comment, requestUserId);
        comment.update(request.getContent());
        return comment;
    }

    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public int countByPostId(Long postId) {
        return findByPostId(postId).size();
    }

    private Comment findCommentInPost(Long postId, Long commentId) {
        // URL의 postId와 댓글이 실제로 속한 게시글 번호가 같은지 확인합니다.
        postService.findById(postId);
        Comment comment = findById(commentId);
        if (!comment.getPostId().equals(postId)) {
            throw new NotFoundException("comment_not_found");
        }
        return comment;
    }

    private void checkAuthor(Comment comment, Long requestUserId) {
        if (!comment.getUserId().equals(requestUserId)) {
            throw new ForbiddenException("forbidden_author");
        }
    }
}
