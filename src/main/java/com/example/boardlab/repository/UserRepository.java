package com.example.boardlab.repository;

import com.example.boardlab.domain.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** DB 대신 List에 사용자 데이터를 저장하는 메모리 저장소입니다. */
@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private Long sequence = 1L;

    /** 서버가 시작될 때 API 테스트에 사용할 기본 사용자를 준비합니다. */
    @PostConstruct
    public void initDummyData() {
        users.add(new User(sequence++, "test@gmail.com", "test1234", "startup", "profile1.png"));
        users.add(new User(sequence++, "user2@gmail.com", "password12", "test123", "profile2.png"));
    }

    public User save(User user) {
        // DB의 자동 증가 ID 대신 sequence로 사용자 번호를 만듭니다.
        User savedUser = new User(sequence++, user.getEmail(), user.getPassword(), user.getNickname(), user.getProfileImage());
        users.add(savedUser);
        return savedUser;
    }

    public Optional<User> findById(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    public boolean existsByNickname(String nickname) {
        return users.stream().anyMatch(user -> user.getNickname().equals(nickname));
    }

    public boolean existsByNicknameExceptUser(String nickname, Long userId) {
        return users.stream()
                .anyMatch(user -> !user.getId().equals(userId) && user.getNickname().equals(nickname));
    }

    public void delete(User user) {
        users.remove(user);
    }
}
