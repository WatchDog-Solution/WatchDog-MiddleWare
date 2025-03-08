package com.watchdog.common.auth.domain.repository;

import com.watchdog.common.auth.domain.entity.TimeStampToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TimeStampTokenRepository extends CrudRepository<TimeStampToken, String> {
    Optional<TimeStampToken> findTimeStampToken(String timeStampToken);
}
