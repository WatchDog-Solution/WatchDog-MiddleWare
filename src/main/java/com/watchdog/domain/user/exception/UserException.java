package com.watchdog.domain.user.exception;

import com.watchdog.domain.user.status.UserErrorStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException{
    private final UserErrorStatus userErrorStatus;

    @Override
    public String getMessage() {
        return userErrorStatus.getMessage();
    }
}
