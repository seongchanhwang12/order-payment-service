package dev.chan.orderpaymentservice.repository.order.query;

import java.math.BigDecimal;

public interface OrderProductProjection {

    Long getId();
    long getProductId();
    String getProductName();
    int getQuantity();
    BigDecimal productPrice();
    BigDecimal totalPriceAmount();



}
