package com.example.boardlab.service;

import com.example.boardlab.domain.Comment;
import com.example.boardlab.dto.comment.CommentRequestDto;
import com.example.boardlab.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


//댓글(비즈니스 로직들) 생성, 조회 삭
@Service
public class CommentService {

    // DB 대신 사용할 댓글 임시 저장소
    private final List<Comment> comments = new ArrayList<>();
    // 댓글 ID 자동 증가용 변수
    private Long sequence = 1L;


    // 댓글 생성 기능
    //postId는 URL에서 받고, userId와 content는 요청 Body에서 받는다.
    public Comment createComment(Long postId, CommentRequestDto requestDto) {

        Comment comment = new Comment(
                sequence++,
                postId,
                requestDto.getUserId(),
                requestDto.getContent()
        );

        comments.add(comment);

        return comment;
    }

    // 댓글 단건 조회 기능
    public Comment findById(Long commentId) {

        for (Comment comment : comments) {
            if (comment.getId().equals(commentId)) {
                return comment;
            }
        }

        throw new NotFoundException("comment_not_found");
    }

    // 댓글 삭제 기능
    public void deleteComment(Long commentId) {

        Comment comment = findById(commentId);

        comments.remove(comment);
    }

    // 댓글 수정 기능
    public Comment updateComment(Long commentId, CommentRequestDto requestDto) {

        Comment comment = findById(commentId);

        comment.update(requestDto.getContent());

        return comment;
    }

    //게시글 댓글

    // 특정 게시글의 댓글 목록 조회
    public List<Comment> findByPostId(Long postId) {
        return comments.stream()
                .filter(comment -> comment.getPostId().equals(postId))
                .toList();
    }

    // 특정 게시글의 댓글 개수 조회
    public int countByPostId(Long postId) {
        return findByPostId(postId).size();
    }



}