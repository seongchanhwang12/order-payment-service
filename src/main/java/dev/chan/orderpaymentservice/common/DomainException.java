package dev.chan.orderpaymentservice.common;

import lombok.Getter;

@Getter
public abstract class DomainException extends RuntimeException {
    private final ErrorCode errorCode;

    protected DomainException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
