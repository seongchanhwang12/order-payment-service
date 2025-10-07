package dev.chan.orderpaymentservice.domain;

import dev.chan.orderpaymentservice.domain.order.Order;

/**
 * Order 스텁 팩토리
 * <br/> long memberId = 1L
 */
public class OrderMother {
    public static Order any() {
        long memberId = 1L;
        return Order.create(memberId);
    }
}
