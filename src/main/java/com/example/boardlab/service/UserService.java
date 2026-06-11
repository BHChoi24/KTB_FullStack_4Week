package com.example.boardlab.service;

import com.example.boardlab.domain.Post;
import com.example.boardlab.domain.User;
import com.example.boardlab.dto.user.PasswordUpdateRequestDto;
import com.example.boardlab.dto.user.UserProfileUpdateRequestDto;
import com.example.boardlab.dto.user.UserSignupRequestDto;
import com.example.boardlab.exception.AuthenticationException;
import com.example.boardlab.exception.InvalidInputException;
import com.example.boardlab.exception.NotFoundException;
import com.example.boardlab.repository.CommentRepository;
import com.example.boardlab.repository.PostRepository;
import com.example.boardlab.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public UserService(
            UserRepository userRepository,
            PostRepository postRepository,
            CommentRepository commentRepository
    ) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public User signup(UserSignupRequestDto request) {
        // 1. 어노테이션만으로 확인하기 어려운 중복값을 검사합니다.
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new InvalidInputException("email", "email_duplicate");
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new InvalidInputException("nickname", "nickname_duplicate");
        }
        // 2. 비밀번호와 비밀번호 확인 값이 같은지 검사합니다.
        if (!request.getPassword().equals(request.getPasswordCheck())) {
            throw new InvalidInputException("password_check", "password_check_invalid");
        }
        // 3. 모든 검사를 통과한 사용자를 저장합니다.
        return userRepository.save(new User(
                null,
                request.getEmail(),
                request.getPassword(),
                request.getNickname(),
                request.getProfileImage()
        ));
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user_not_found"));
    }

    public User requireAuthenticated(Long userId) {
        // Spring Security를 사용하지 않으므로 X-USER-ID 헤더로 로그인 여부를 확인합니다.
        if (userId == null) {
            throw new AuthenticationException("unauthorized");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new AuthenticationException("unauthorized"));
    }

    public User login(String email, String password) {
        // 이메일과 비밀번호 중 하나라도 일치하지 않으면 같은 401 응답을 보냅니다.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("email_password_check"));

        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("email_password_check");
        }
        return user;
    }

    public User updateProfile(Long userId, UserProfileUpdateRequestDto request) {
        // 1. 로그인한 사용자인지 확인합니다.
        User user = requireAuthenticated(userId);

        // 2. 다른 사용자가 이미 사용 중인 닉네임인지 확인합니다.
        if (userRepository.existsByNicknameExceptUser(request.getNickname(), userId)) {
            throw new InvalidInputException("nickname", "nickname_duplicate");
        }
        // 3. 검사를 통과하면 프로필을 변경합니다.
        user.updateProfile(request.getNickname(), request.getProfileImage());
        return user;
    }

    public void updatePassword(Long userId, PasswordUpdateRequestDto request) {
        // 1. 로그인 확인
        User user = requireAuthenticated(userId);

        // 2. 새 비밀번호와 확인 값 비교
        if (!request.getPassword().equals(request.getPasswordCheck())) {
            throw new InvalidInputException("password_check", "password_check_not_same");
        }
        // 3. 비밀번호 변경
        user.updatePassword(request.getPassword());
    }

    public void delete(Long userId) {
        // 1. 로그인한 사용자를 찾습니다.
        User user = requireAuthenticated(userId);

        // 2. 사용자가 작성한 게시글에 달린 댓글을 삭제합니다.
        for (Post post : postRepository.findByUserId(userId)) {
            commentRepository.deleteAllByPostId(post.getId());
        }

        // 3. 사용자가 작성한 댓글과 게시글을 삭제합니다.
        commentRepository.deleteAllByUserId(userId);
        postRepository.deleteAllByUserId(userId);

        // 4. 마지막으로 사용자 정보를 삭제합니다.
        userRepository.delete(user);
    }
}
