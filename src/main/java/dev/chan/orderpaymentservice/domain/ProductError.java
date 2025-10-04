package dev.chan.orderpaymentservice.domain;

import dev.chan.orderpaymentservice.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum ProductError implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "PRD-NOT_FOUND","product.error.notfound"),
    INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST,"PRD-INSUFFICIENT_STOCK" , "product.error.insufficient_stock" ),
    ;

    private final HttpStatus status;
    private final String code;
    private final String messageKey;

}
