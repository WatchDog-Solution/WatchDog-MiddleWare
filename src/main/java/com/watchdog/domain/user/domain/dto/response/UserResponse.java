package com.watchdog.domain.user.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class UserResponse {

    @Data
    @Builder
    @AllArgsConstructor
    public static class UserDto {
        private Long userId;
        private String nickName;
        private String status;
        private String accessToken;
        private String refreshToken;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class UserInfoDto {
        private String nickName;
        private String status;
    }
}
