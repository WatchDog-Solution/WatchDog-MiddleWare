package com.watchdog.domain.user.application;

import com.watchdog.common.auth.domain.entity.RefreshToken;
import com.watchdog.common.auth.domain.repository.RefreshTokenRepository;
import com.watchdog.common.auth.jwt.JwtUtil;
import com.watchdog.common.auth.jwt.exception.TokenException;
import com.watchdog.common.auth.jwt.status.TokenErrorStatus;
import com.watchdog.domain.user.domain.converter.UserConverter;
import com.watchdog.domain.user.domain.dto.request.UserRequest;
import com.watchdog.domain.user.domain.dto.response.UserResponse;
import com.watchdog.domain.user.domain.entity.User;
import com.watchdog.domain.user.domain.status.Status;
import com.watchdog.domain.user.exception.UserException;
import com.watchdog.domain.user.status.UserErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserSubService userSubService;

    /**
     * 회원가입
     * @param registerToken
     * @param userRegisterDto
     * @return
     */

    @Transactional
    public UserResponse.UserDto registerUser(String registerToken, UserRequest.UserRegisterDto userRegisterDto) {
        validRegisterToken(registerToken);
        validateUserInfo(userRegisterDto.getNickName());

        String providerid = jwtUtil.getProviderIdFromToken(registerToken);
        
        checkExistUser(providerid);

        // 새로운 유저 생성
        User newUser = UserConverter.toUserEntity(userRegisterDto, providerid);
        User savedUser = userSubService.saveUser(newUser);

        String refreshToken = jwtUtil.generateRefreshToken(savedUser.getUserId());
        saveRefreshToken(refreshToken, savedUser);

        String accessToken = jwtUtil.generateAccessToken(savedUser.getUserId());

        return UserConverter.toUserDto(savedUser, accessToken, refreshToken);
    }

    private void validRegisterToken(String registerToken) {
        if (!jwtUtil.isRegisterTokenValid(registerToken))
            throw new TokenException(TokenErrorStatus.INVALID_REGISTER_TOKEN);
    }

    private void checkExistUser(String providerId) {
        if (userSubService.IsUserExistByProviderId(providerId))
            throw new UserException(UserErrorStatus.ALREADY_EXIST_USER);
    }

    private void saveRefreshToken(String refreshToken, User user) {
        RefreshToken newRefreshToken = RefreshToken.of(refreshToken, user.getUserId());
        refreshTokenRepository.save(newRefreshToken);
    }

    /**
     * 로그아웃
     * @param refreshToken
     */
    @Transactional
    public void logoutUser(String refreshToken) {
        deleteRefreshTokenInRedis(refreshToken);
    }

    /**
     * 회원 탈퇴
     * @param userId
     * @param refreshToken
     */
    @Transactional
    public void deleteUser(Long userId, String refreshToken) {
        // 연관된 데이터 삭제
        userSubService.deleteUserByUserId(userId);
        deleteRefreshTokenInRedis(refreshToken);
    }

    private void deleteRefreshTokenInRedis(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            log.info("쿠키에 리프레쉬 토큰 없음");
            return;
        }
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByRefreshToken(refreshToken);
        refreshTokenOptional.ifPresent(refreshTokenRepository::delete);
    }

    /**
     * 유저 정보 수정
     * @param userId
     * @param userUpdateDto
     */
    @Transactional
    public void updateUser(Long userId, UserRequest.UserUpdateDto userUpdateDto) {
        User user = userSubService.getUser(userId);

        if(userUpdateDto.getNickName() != null) {
            validateUserInfo(userUpdateDto.getNickName());
            user.setNickName(userUpdateDto.getNickName());
        }

        if(userUpdateDto.getStatus() != null) {
            user.setStatus(Status.getStatus(userUpdateDto.getStatus()));
        }
    }

    private void validateUserInfo(String nickName) {
        if (nickName == null || nickName.isEmpty() || nickName.length() > 10) {
            throw new UserException(UserErrorStatus.INVALID_USER_NICKNAME);
        }

        // 한글, 영어, 숫자, 공백만 허용
        String nicknamePattern = "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣\s]*$";
        if (!Pattern.matches(nicknamePattern, nickName)) {
            throw new UserException(UserErrorStatus.INVALID_USER_NICKNAME);
        }
    }

    /**
     * 유저 정보 조회
     * @param userId
     * @return
     */
//    @Transactional
//    public UserResponse.UserInfoDto getUserInfo(Long userId) {
//        User user = userSubService.getUser(userId);
//        return 'test';
//    }
}
