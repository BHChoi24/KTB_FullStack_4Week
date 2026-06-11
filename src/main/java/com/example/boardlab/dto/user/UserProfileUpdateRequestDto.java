package com.example.boardlab.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserProfileUpdateRequestDto {
    @NotBlank(message = "nickname_empty")
    @Size(max = 10, message = "nickname_invalid")
    private String nickname;

    private String profileImage;

    public String getNickname() { return nickname; }
    public String getProfileImage() { return profileImage; }
}
