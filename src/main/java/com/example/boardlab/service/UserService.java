package com.example.boardlab.service;

import com.example.boardlab.domain.User;
import com.example.boardlab.dto.user.UserSignupRequestDto;
import com.example.boardlab.exception.NotFoundException;
import com.example.boardlab.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * [클래스 역할] 회원 데이터 처리에 대한 핵심 비즈니스 규칙을 수행하는 서비스 클래스입니다.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원 가입을 처리한 후 영속 저장소(Repository)에 영구 위임합니다.
     */
    public User signup(UserSignupRequestDto requestDto) {
        User user = new User(null, requestDto.getEmail(), requestDto.getPassword(), requestDto.getNickname(), requestDto.getProfileImage());
        return userRepository.save(user);
    }

    /**
     * 회원 단건 식별을 수행하며, 매칭되는 메모리가 전혀 없을 경우 404 규격인 user_not_found를 던집니다.
     */
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user_not_found"));
    }

    /**
     * 로그인 비즈니스 로직: 저장소에서 이메일로 검색 후 평문 암호 검증을 가내수공업으로 직접 수행합니다.
     * 불일치 시 시트 요구 응답 코드인 'email_password_check' 예외 문자열을 발생시킵니다.
     */
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("email_password_check"));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("email_password_check");
        }
        return user;
    }
}