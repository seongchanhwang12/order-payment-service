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
    INVALID_SESSION(HttpStatus.UNAUTHORIZED, "MEM-INVALID_SESSION" ,"member.error.invalid_session"),
    PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED,"MEM-PASSWORD_MISMATCH" ,"member.error.invalid_credentials" ),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "MEM-UNAUTHORIZED" ,"member.error.unauthorized" ),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "MEM-DUPLICATE_EMAIL" ,"member.error.duplicate_email" ),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEM-NOT_FOUND", "member.error.not_found" ),
    ;

    private final HttpStatus status;
    private final String code;
    private final String messageKey;

}
