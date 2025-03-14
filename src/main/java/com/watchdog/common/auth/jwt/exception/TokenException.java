package com.watchdog.common.auth.jwt.exception;

import com.watchdog.common.auth.jwt.status.TokenErrorStatus;
import com.watchdog.common.base.BaseErrorStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenException extends RuntimeException {
    private final TokenErrorStatus errorStatus;

    @Override
    public String getMessage() {
        return errorStatus.getMessage();
    }
}
