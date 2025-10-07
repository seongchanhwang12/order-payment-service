package dev.chan.orderpaymentservice.repository.order;

import dev.chan.orderpaymentservice.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, Long> {
}
