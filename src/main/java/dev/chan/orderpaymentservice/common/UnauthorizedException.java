package dev.chan.orderpaymentservice.common;

/**
 * 인증 실패시 예외
 */
public class UnauthorizedException extends DomainException {
    public UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
