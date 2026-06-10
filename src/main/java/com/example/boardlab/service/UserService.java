package com.example.boardlab.service;

import com.example.boardlab.domain.User;
import com.example.boardlab.dto.user.UserSignupRequestDto;
import com.example.boardlab.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// User 비즈니스 로직을 처리하는 서비스
@Service
public class UserService {

    // DB 대신 사용할 임시 저장소
    private final List<User> users = new ArrayList<>();

    // 사용자 ID 자동 증가용 변수
    private Long sequence = 1L;

    // 회원가입 기능
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

    // 회원 단건 조회 기능
    public User findById(Long userId) {

        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }

        throw new NotFoundException("user_not_found");
    }
}