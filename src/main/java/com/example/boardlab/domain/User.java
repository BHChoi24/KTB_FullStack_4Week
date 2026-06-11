package com.example.boardlab.domain;

/**
 * [클래스 역할] 회원(User)의 핵심 원본 데이터를 보관하는 도메인 모델입니다.
 * 실제 현업에서는 데이터베이스의 회원 테이블과 1:1로 매핑되는 Entity 역할을 수행합니다.
 */
public class User {
    private Long id;              // 회원의 고유 식별 번호 (저장소에서 자동 발급)
    private String email;         // 로그인 시 식별자로 사용하는 이메일 주소
    private String password;      // 인증을 위한 비밀번호 (현재 평문 저장)
    private String nickname;      // 게시글이나 댓글 작성 시 노출되는 사용자 닉네임
    private String profileImage;  // 사용자의 프로필 이미지 파일명 또는 URL

    public User(Long id, String email, String password, String nickname, String profileImage) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    // [Getter 메서드] 외부 계층(Service, DTO)에서 도메인의 필드 값을 읽기 위해 사용합니다.
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getNickname() { return nickname; }
    public String getProfileImage() { return profileImage; }
}