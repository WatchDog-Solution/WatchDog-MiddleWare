package com.watchdog.domain.user.domain.status;

import com.watchdog.domain.user.exception.UserException;
import com.watchdog.domain.user.status.UserErrorStatus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    ADMIN("관리자"),
    EMPLOYMENT("직원"),
    USER("사용자");

    private final String value;

    public String getValue() {
        return value;
    }

    public static Status getStatus(String value) {
        for (Status status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new UserException(UserErrorStatus.INVALID_USER_STATUS);
    }

}
