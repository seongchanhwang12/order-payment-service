package dev.chan.orderpaymentservice.domain.order.port;

import dev.chan.orderpaymentservice.domain.order.entity.OrderProduct;

import java.util.List;
import java.util.Optional;

public interface OrderProductRepository {
    void save(OrderProduct orderProduct);

    Optional<OrderProduct> findById(Long id);

    List<OrderProduct> findAllByOrderId(Long aLong);

    long count();
}
