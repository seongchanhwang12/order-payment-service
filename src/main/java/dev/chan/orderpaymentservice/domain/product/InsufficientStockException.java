package dev.chan.orderpaymentservice.domain.product;

import dev.chan.orderpaymentservice.common.PolicyViolationException;

/**
 * 재고 불충분 예외
 * 주문량 대비 재고가 불충분할 경우 발생하는 예외입니다.
 */
public class InsufficientStockException extends PolicyViolationException {

    public InsufficientStockException(int quantity) {
        super(ProductError.INSUFFICIENT_STOCK , String.valueOf(quantity));
    }
}
