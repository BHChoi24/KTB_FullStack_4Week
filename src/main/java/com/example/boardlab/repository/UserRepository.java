package com.example.boardlab.repository;

import com.example.boardlab.domain.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private Long sequence = 1L;

    // 서버가 켜질 때 자동으로 더미 데이터 로드
    @PostConstruct
    public void initDummyData() {
        users.add(new User(sequence++, "test@gmail.com", "test1234", "startup", "profile1.png"));
        users.add(new User(sequence++, "user2@gmail.com", "password12", "test123", "profile2.png"));
    }

    public User save(User user) {
        User savedUser = new User(sequence++, user.getEmail(), user.getPassword(), user.getNickname(), user.getProfileImage());
        users.add(savedUser);
        return savedUser;
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public Optional<User> findById(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return users.stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }
}