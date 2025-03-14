package com.watchdog.domain.user.domain.converter;


import com.watchdog.domain.user.domain.dto.request.UserRequest;
import com.watchdog.domain.user.domain.dto.response.UserResponse;
import com.watchdog.domain.user.domain.entity.User;
import com.watchdog.domain.user.domain.status.Status;

public class UserConverter {

    public static User toUserEntity(UserRequest.UserRegisterDto request, String providerId) {
        return User.builder()
                .providerId(providerId)
                .nickName(request.getNickName())
                .status(Status.getStatus(request.getStatus()))
                .build();
    }

    public static UserResponse.UserDto toUserDto(User user, String accessToken, String refreshToken) {
        return UserResponse.UserDto.builder()
                .userId(user.getUserId())
                .nickName(user.getNickName())
                .status(user.getStatus().getValue())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
