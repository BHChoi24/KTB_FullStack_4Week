package com.example.boardlab.dto.user;

import com.example.boardlab.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserSignupResponseDto {

    @JsonProperty("user_id")
    private Long userId;

    public UserSignupResponseDto(User user) {
        this.userId = user.getId();
    }
}