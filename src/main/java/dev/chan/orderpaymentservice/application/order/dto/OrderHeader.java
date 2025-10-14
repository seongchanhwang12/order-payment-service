package dev.chan.orderpaymentservice.application.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderHeader(
        long orderId,
        long orderedBy,
        LocalDateTime orderedAt,
        BigDecimal totalPrice){

}
