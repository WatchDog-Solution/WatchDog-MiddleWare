package com.watchdog.common.auth.application;

import com.watchdog.common.auth.domain.entity.RefreshToken;
import com.watchdog.common.auth.domain.entity.TimeStampToken;
import com.watchdog.common.auth.domain.repository.RefreshTokenRepository;
import com.watchdog.common.auth.domain.repository.TimeStampTokenRepository;
import com.watchdog.common.auth.jwt.JwtUtil;
import com.watchdog.common.auth.jwt.exception.TokenException;
import com.watchdog.common.auth.jwt.status.TokenErrorStatus;
import com.watchdog.domain.user.application.UserSubService;
import com.watchdog.domain.user.domain.converter.UserConverter;
import com.watchdog.domain.user.domain.dto.response.UserResponse;
import com.watchdog.domain.user.domain.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final UserSubService userSubService;
    private final TimeStampTokenRepository timeStampTokenRepository;

    /**
     * 임시 토큰을 이용하여 AccessToken과 RefreshToken을 발급한다.
     * @param timeStampToken
     * @return
     */
    @Transactional
    public UserResponse.UserDto issueTokens(String timeStampToken) {
        // 임시 토큰 유효성 검증
        TimeStampToken tmpTokenEntity = validateTmpToken(timeStampToken);
        timeStampTokenRepository.delete(tmpTokenEntity);

        // 새 RefreshToken 발급 및 저장
        Long userId = Long.parseLong(jwtUtil.getUserIdFromTmpToken(timeStampToken));
        String refreshToken = jwtUtil.generateRefreshToken(userId);
        RefreshToken newRefreshToken = RefreshToken.of(refreshToken, userId);
        refreshTokenRepository.save(newRefreshToken);

        // 새 AccessToken 발급
        String accessToken = jwtUtil.generateAccessToken(userId);

        User user = userSubService.findUserById(userId);
        return UserConverter.toUserDto(user, accessToken, refreshToken);
    }

    /**
     * RefreshToken을 이용하여 새로운 AccessToken을 발급한다.
     * @param refreshToken
     * @return
     */
    @Transactional
    public String reissueAccessToken(String refreshToken) {
        validateRefreshToken(refreshToken);

        Long userId = Long.parseLong(jwtUtil.getUserIdFromRefreshToken(refreshToken));

        return jwtUtil.generateAccessToken(userId);
    }

    private void validateRefreshToken(String refreshToken) {
        if (!jwtUtil.isRefreshTokenValid(refreshToken)) {
            throw new TokenException(TokenErrorStatus.INVALID_REFRESH_TOKEN);
        }

        refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new TokenException(TokenErrorStatus.REFRESH_TOKEN_NOT_FOUND));
    }

    private TimeStampToken validateTmpToken(String tmpToken) {
        if (!jwtUtil.isTmpTokenValid(tmpToken)) {
            throw new TokenException(TokenErrorStatus.INVALID_TMP_TOKEN);
        }
        return timeStampTokenRepository.findTimeStampToken(tmpToken)
                .orElseThrow(() -> new TokenException(TokenErrorStatus.TMP_TOKEN_NOT_FOUND));
    }
}
