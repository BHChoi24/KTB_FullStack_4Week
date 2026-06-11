package com.example.boardlab.service;

import com.example.boardlab.domain.User;
import com.example.boardlab.dto.user.UserSignupRequestDto;
import com.example.boardlab.exception.NotFoundException;
import com.example.boardlab.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signup(UserSignupRequestDto requestDto) {
        User user = new User(null, requestDto.getEmail(), requestDto.getPassword(), requestDto.getNickname(), requestDto.getProfileImage());
        return userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user_not_found"));
    }

    // 시트 기준 로그인 구현체 분리
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("email_password_check"));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("email_password_check");
        }
        return user;
    }
}