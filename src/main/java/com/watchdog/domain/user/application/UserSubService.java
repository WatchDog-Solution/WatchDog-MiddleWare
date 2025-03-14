package com.watchdog.domain.user.application;

import com.watchdog.common.exception.GeneralException;
import com.watchdog.common.status.ErrorStatus;
import com.watchdog.domain.user.domain.entity.User;
import com.watchdog.domain.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSubService {
    private final UserRepository userRepository;

    @Transactional
    public void deleteUserByUserId(Long userId) {
        userRepository.deleteUserByUserId(userId);
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean IsUserExistByProviderId(String providerId) {
        return userRepository.existsByProviderId(providerId);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.UNAUTHORIZED));
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.UNAUTHORIZED));
    }
}
