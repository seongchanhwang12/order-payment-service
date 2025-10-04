package dev.chan.orderpaymentservice.domain.order;

import dev.chan.orderpaymentservice.common.PolicyViolationException;

public class OrderProductRequiredException extends PolicyViolationException {
    public OrderProductRequiredException() {
        super(OrderError.ORDER_PRODUCT_REQUIRED, "An order must include at least one product.");
    }
}
