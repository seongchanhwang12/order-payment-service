package dev.chan.orderpaymentservice.repository.order.query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OrderProjection {
    Long getId();
    long getOrderedBy();
    LocalDateTime getOrderedAt();
    BigDecimal getTotalPriceAmount();


}
