package com.example.boardlab.service;

import com.example.boardlab.domain.Post;
import com.example.boardlab.dto.post.PostCreateRequestDto;
import com.example.boardlab.dto.post.PostUpdateRequestDto;
import com.example.boardlab.exception.ForbiddenException;
import com.example.boardlab.exception.InvalidInputException;
import com.example.boardlab.exception.InvalidPageException;
import com.example.boardlab.exception.NotFoundException;
import com.example.boardlab.repository.CommentRepository;
import com.example.boardlab.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    public PostService(
            PostRepository postRepository,
            CommentRepository commentRepository,
            UserService userService
    ) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public Post createPost(Long userId, PostCreateRequestDto request) {
        // 1. 로그인한 사용자인지 확인합니다.
        userService.requireAuthenticated(userId);

        // 2. 요청 DTO를 Post 객체로 바꾸어 저장합니다.
        return postRepository.save(new Post(
                null, userId, request.getTitle(), request.getContent(), request.getImageUrl()
        ));
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("posts_not_found"));
    }

    public List<Post> findPage(int page, int size) {
        // 페이지 번호는 1부터 시작합니다.
        if (page < 1) throw new InvalidPageException();

        List<Post> posts = postRepository.findAll();
        int fromIndex = (page - 1) * size;

        // 게시글이 있는데 시작 위치가 전체 개수보다 크면 없는 페이지입니다.
        if (fromIndex >= posts.size() && !posts.isEmpty()) throw new InvalidPageException();

        int toIndex = Math.min(fromIndex + size, posts.size());
        return posts.subList(fromIndex, toIndex);
    }

    public Post findByIdAndIncreaseView(Long postId) {
        Post post = findById(postId);
        post.increaseViewsCount();
        return post;
    }

    public Post updatePost(Long postId, Long requestUserId, PostUpdateRequestDto request) {
        // 1. 로그인 확인
        userService.requireAuthenticated(requestUserId);

        // 2. 게시글 존재 여부와 작성자 권한 확인
        Post post = findById(postId);
        checkAuthor(post, requestUserId);

        // 3. PATCH에서 null은 미전달, 빈 문자열은 잘못된 입력입니다.
        if (request.getTitle() != null && request.getTitle().isBlank()) {
            throw new InvalidInputException("title", "title_empty");
        }
        if (request.getContent() != null && request.getContent().isBlank()) {
            throw new InvalidInputException("content", "content_empty");
        }
        // 4. 전달된 값만 변경합니다.
        post.update(request.getTitle(), request.getContent(), request.getImageUrl());
        return post;
    }

    public void deletePost(Long postId, Long requestUserId) {
        // 1. 로그인 확인
        userService.requireAuthenticated(requestUserId);

        // 2. 게시글 존재 여부와 작성자 권한 확인
        Post post = findById(postId);
        checkAuthor(post, requestUserId);

        // 3. 게시글 아래 댓글을 먼저 삭제한 후 게시글을 삭제합니다.
        commentRepository.deleteAllByPostId(postId);
        postRepository.delete(post);
    }

    private void checkAuthor(Post post, Long requestUserId) {
        if (!post.getUserId().equals(requestUserId)) {
            throw new ForbiddenException("forbidden_author");
        }
    }
}
