package dev.chan.orderpaymentservice.domain.order.port;

import dev.chan.orderpaymentservice.application.order.dto.OrderDetail;

import java.util.Optional;

public interface OrderQueryRepository {
    Optional<OrderDetail> findDetailById(long orderId);
}
