package dev.chan.orderpaymentservice.domain.order.port;

import dev.chan.orderpaymentservice.repository.order.query.OrderProductProjection;

import java.util.List;

public interface OrderProductQueryRepository {
    List<OrderProductProjection> findProductsByOrderId(long orderId);
}
