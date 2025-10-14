package dev.chan.orderpaymentservice.domain.order;

import dev.chan.orderpaymentservice.domain.order.entity.Order;

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
