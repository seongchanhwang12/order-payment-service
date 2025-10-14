package dev.chan.orderpaymentservice.domain.order.exception;

import dev.chan.orderpaymentservice.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum OrderError implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "ORD-NOT_FOUND","order.error.not_found"),
    ORDER_PRODUCT_REQUIRED(HttpStatus.BAD_REQUEST,"ORD-ORDER_PRODUCT_REQUIRED" , "order.error.order_product_required" ),
    ;

    private final HttpStatus status;
    private final String code;
    private final String messageKey;


}
