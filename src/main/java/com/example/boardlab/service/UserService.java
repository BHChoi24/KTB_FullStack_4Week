package com.example.boardlab.service;

import com.example.boardlab.domain.User;
import com.example.boardlab.dto.user.UserSignupRequestDto;
import com.example.boardlab.dto.user.UserSignupResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//User 비즈니스 로직 생성, 조줌
@Service
public class UserService {

    //DB 대신 사용할 임시 저장소 -> 과제목표가 하드코딩으로 진행
    private final List<User> users = new ArrayList<>();

    //사용자 ID 자동 증가용 변수 -> 가입순서로 아이디값 증가로 진행
    private Long sequence = 1L;

    //회원가입 기능
    public User signup(UserSignupRequestDto requestDto) {

        User user = new User(
                sequence++,
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getNickname(),
                requestDto.getProfileImage()
        );

        users.add(user);

        return user;
    }

        users.add(user);

        return user;
    }

    //회원 단건 조회 기능
    //users 리스트에서 id가 일치하는 회원을 찾아 반환->DB가 없어서 리스트에서 반복문으로 찾기
    public User findById(Long userId) {

        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }

        throw new IllegalArgumentException("comment_not_found");
    }

}