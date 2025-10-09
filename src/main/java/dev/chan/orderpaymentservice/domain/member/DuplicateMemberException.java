package dev.chan.orderpaymentservice.domain.member;

import dev.chan.orderpaymentservice.common.ErrorCode;
import dev.chan.orderpaymentservice.common.PolicyViolationException;
import lombok.Getter;

@Getter
public class DuplicateMemberException extends PolicyViolationException {
    public DuplicateMemberException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
