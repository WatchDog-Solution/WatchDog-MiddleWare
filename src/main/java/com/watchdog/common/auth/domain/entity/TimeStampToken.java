package com.watchdog.common.auth.domain.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "timeStampToken", timeToLive = 3600000)
@AllArgsConstructor
@Builder
public class TimeStampToken {
    @Id
    private String timeStampToken;
    private Long userId;

    @Builder
    public static TimeStampToken of(String timeStampToken, Long userId) {
        return TimeStampToken.builder()
                .timeStampToken(timeStampToken)
                .userId(userId)
                .build();
    }
}
