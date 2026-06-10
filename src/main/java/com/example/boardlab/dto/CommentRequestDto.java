package com.example.boardlab.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * 댓글 작성 요청을 받기 위한 DTO
 */
@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private Long userId; //시트 작성할때는 로그인 상태라고 가졍해서 없었는데 여기서는 작성를 알기위해 추가
    private String content;
}