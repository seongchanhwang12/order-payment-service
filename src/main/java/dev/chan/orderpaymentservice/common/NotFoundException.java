package dev.chan.orderpaymentservice.common;

public class NotFoundException extends DomainException {
    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
