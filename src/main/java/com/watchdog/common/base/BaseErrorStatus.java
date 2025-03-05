package com.watchdog.common.base;

import org.springframework.http.HttpStatus;

public interface BaseErrorStatus {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
