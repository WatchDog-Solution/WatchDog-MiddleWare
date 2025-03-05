package com.watchdog.common.exception;

import ch.qos.logback.core.status.ErrorStatus;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {
    private final ErrorStatus errorStatus;

    public GeneralException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }
}
