package dev.chan.orderpaymentservice.repository.common;

import java.math.BigDecimal;

public interface MoneyProjection {
    BigDecimal getAmount();
}
