package dev.chan.orderpaymentservice.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum CommonError implements ErrorCode{

    ARGUMENT_ERROR(HttpStatus.BAD_REQUEST,"COMMON-INVALID_ARGUMENT", "common.errors.invalid_argument");

    private final HttpStatus status;
    private final String code;
    private final String messageKey;




}
