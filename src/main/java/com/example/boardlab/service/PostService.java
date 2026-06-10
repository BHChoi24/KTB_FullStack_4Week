package com.example.boardlab.service;

import com.example.boardlab.domain.Post;
import com.example.boardlab.dto.PostRequestDto;
import com.example.boardlab.dto.post.PostCreateRequestDto;
import com.example.boardlab.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


//게시글(비즈니스로직) 생성, 조회, 삭제
@Service
public class PostService {
    // DB 대신 사용할 게시글 임시 저장소,
    private final List<Post> posts = new ArrayList<>();
    // 게시글 ID 자동 증가용 변수
    private Long sequence = 1L;


    // 게시글 작성 생성 기능
    public Post createPost(PostCreateRequestDto requestDto) {

        Post post = new Post(
                sequence++,
                requestDto.getUserId(),
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getImageUrl()
        );

        posts.add(post);

        return post;
    }

    // 게시글 단건 조회 기능
    public Post findById(Long postId) {
        for (Post post : posts) {
            if (post.getId().equals(postId)) {
                return post;
            }
        }
        throw new NotFoundException("post_not_found");
    }

    //게시글 수정 기능
    //동작 : postId로 게시글을 찾음 -> 제목과 내용을 변경
    public Post updatePost(Long postId, PostRequestDto requestDto) {

        Post post = findById(postId); //findById() -> 이미 만들어둔 조회기능 재사용해서 찾고 수정
        post.update(
                requestDto.getTitle(),
                requestDto.getContent()
        );

        return post;
    }

    //게시글 삭제 기능
    //동작 : postId로 게시글을 찾음 -> 리스트에서 삭제
    public void deletePost(Long postId) {

        Post post = findById(postId);
        posts.remove(post);
    }

    //게시글 목록 조회 기능
    //현재 저장된 모든 게시글을 반환한다.
    public List<Post> findAll() {
        return posts;
    }

}