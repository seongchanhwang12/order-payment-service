package dev.chan.orderpaymentservice.domain.order;

import dev.chan.orderpaymentservice.domain.common.Money;
import dev.chan.orderpaymentservice.domain.common.Quantity;
import dev.chan.orderpaymentservice.domain.order.entity.Order;
import dev.chan.orderpaymentservice.domain.order.entity.OrderProduct;

/**
 * OrderProduct 스텁 팩토리
 */
public class OrderProductMother {
    /* 기본 값 */
    private static final Long PRODUCT_ID = 1L;
    private static final Quantity QUANTITY = Quantity.of(1);
    private static final Money PRICE = Money.wons(1000);
    private static final String PRODUCT_NAME = "product";

    /**
     * Order를 전달받아 스텁 객체 생성
     *
     * @param order
     * @return
     */
    public static OrderProduct withOrder(Order order) {
        return OrderProduct.of(
                order,
                PRODUCT_ID,
                QUANTITY,
                PRICE,
                PRODUCT_NAME);
    }
}
