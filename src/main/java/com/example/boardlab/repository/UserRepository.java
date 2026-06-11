package com.example.boardlab.repository;

import com.example.boardlab.domain.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * [클래스 역할] 데이터베이스가 없으므로 가상의 메모리 컬렉션(List)을 활용해 유저 데이터를 격리 저장·관리하는 저장소입니다.
 */
@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private Long sequence = 1L; // 수동 회원번호 발급용 시퀀스 계수기

    /**
     * [더미 데이터 적재] 서버가 구동되는 즉시 시트 명세서 흐름에 명시된 기본 유저 2명을 영속 대기시킵니다.
     */
    @PostConstruct
    public void initDummyData() {
        users.add(new User(sequence++, "test@gmail.com", "test1234", "startup", "profile1.png"));
        users.add(new User(sequence++, "user2@gmail.com", "password12", "test123", "profile2.png"));
    }

    /**
     * 메모리 리스트에 수동 객체를 등록하고 인덱스를 자동 발급합니다.
     */
    public User save(User user) {
        User savedUser = new User(sequence++, user.getEmail(), user.getPassword(), user.getNickname(), user.getProfileImage());
        users.add(savedUser);
        return savedUser;
    }

    /**
     * 수동 ID 단건 스트림 탐색기
     */
    public Optional<User> findById(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    /**
     * 로그인 시 이메일 유무 판별 전용 검색 엔진 스트림 구현체
     */
    public Optional<User> findByEmail(String email) {
        return users.stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }
}