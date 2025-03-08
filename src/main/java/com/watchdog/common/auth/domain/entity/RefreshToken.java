package com.watchdog.common.auth.domain.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 604800000)
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    private String refreshToken;
    private Long userId;

    @Builder
    public static RefreshToken of(String refreshToken, Long userId) {
        return RefreshToken.builder()
                .refreshToken(refreshToken)
                .userId(userId)
                .build();
    }
}
