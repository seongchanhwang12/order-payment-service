package dev.chan.orderpaymentservice.domain;

import java.math.BigDecimal;

public record Money(BigDecimal amount) {
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money amount cannot be negative");
        }
    }

    public Money add(Money money) {
        return new Money(this.amount.add(money.amount));
    }

    public Money multiply(int value) {
        BigDecimal decimal = BigDecimal.valueOf(value);
        return new Money(this.amount.multiply(decimal));
    }
}
