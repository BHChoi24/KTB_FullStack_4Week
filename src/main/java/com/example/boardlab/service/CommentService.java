package com.example.boardlab.service;

import com.example.boardlab.domain.Comment;
import com.example.boardlab.dto.comment.CommentRequestDto;
import com.example.boardlab.exception.NotFoundException;
import com.example.boardlab.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * [클래스 역할] 특정 게시물 하위의 댓글 조작 및 권한 소유주 대조를 전담하는 서비스 클래스입니다.
 */
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * 댓글을 생성 계층 저장소에 누적 저장 지시합니다.
     */
    public Comment createComment(Long postId, CommentRequestDto requestDto) {
        Comment comment = new Comment(null, postId, requestDto.getUserId(), requestDto.getContent());
        return commentRepository.save(comment);
    }

    /**
     * 댓글 단건 추적 실패 시 예외 규격화 처리
     */
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("comment_not_found"));
    }

    /**
     * 댓글 삭제 전, 요청자가 실제 댓글 작성자인지 ID 매칭 검사 기법 적용
     */
    public void deleteComment(Long commentId, Long requestUserId) {
        Comment comment = findById(commentId);

        if (!comment.getUserId().equals(requestUserId)) {
            throw new IllegalArgumentException("forbidden_author");
        }

        commentRepository.delete(comment);
    }

    /**
     * 댓글 내용 수정 전 소유주 검증 단계 거친 후 갱신
     */
    public Comment updateComment(Long commentId, Long requestUserId, CommentRequestDto requestDto) {
        Comment comment = findById(commentId);

        if (!comment.getUserId().equals(requestUserId)) {
            throw new IllegalArgumentException("forbidden_author");
        }

        comment.update(requestDto.getContent());
        return comment;
    }

    /**
     * 특정 게시물 일련번호 하위에 종속된 댓글만 스크리닝하여 리스트로 반환합니다.
     */
    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    /**
     * 목록 출력 시 활용할 댓글 누적 카운트 전용 로직
     */
    public int countByPostId(Long postId) {
        return findByPostId(postId).size();
    }
}