package dev.chan.orderpaymentservice.domain.member;

import dev.chan.orderpaymentservice.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@Accessors(fluent = true)
public enum MemberError implements ErrorCode {
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "MEM-INVALID_PASSWORD","member.error.invalid_password"),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "MEM-DUPLICATE_EMAIL" ,"member.duplicate_email" ),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "MEM-INVALID_EMAIL" ,"member.invalid_email"),
    AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, "MEM-AUTHENTICATION_FAIL" ,"member.authentication_fail"),
    ;
    private final HttpStatus status;
    private final String code;
    private final String messageKey;

}
