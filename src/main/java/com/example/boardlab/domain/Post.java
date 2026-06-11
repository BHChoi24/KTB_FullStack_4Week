package com.example.boardlab.domain;

/**
 * [클래스 역할] 게시글(Post)의 핵심 원본 데이터와 게시글 관련 비즈니스 행위를 담당하는 도메인 모델입니다.
 */
public class Post {
    private Long id;             // 게시글의 고유 식별 번호
    private Long userId;         // 게시글을 작성한 회원의 고유 식별자 (작성자 검증 시 활용)
    private String title;        // 게시글 제목
    private String content;      // 게시글 본문 내용
    private String imageUrl;     // 게시글 첨부 이미지 URL (선택 사항)
    private int viewsCount;      // 게시글 조회수 카운터

    public Post(Long id, Long userId, String title, String content, String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.viewsCount = 0; // 초기 생성 시 조회수는 0으로 기본 설정
    }

    /**
     * [도메인 비즈니스 로직] 게시글 상세 조회 시 호출되어 조회수를 1 증가시킵니다.
     */
    public void increaseViewsCount() {
        this.viewsCount++;
    }

    /**
     * [도메인 비즈니스 로직] 게시글 수정 요청 시 내부 데이터를 한 번에 안전하게 변경합니다.
     */
    public void update(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    // [Getter 메서드] 계층 간 데이터 전환 시 값을 추출하기 위해 사용합니다.
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public int getViewsCount() { return viewsCount; }
}