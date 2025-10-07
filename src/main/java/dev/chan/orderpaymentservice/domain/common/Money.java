package dev.chan.orderpaymentservice.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Embeddable
@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Money {
    public static final Money ZERO = Money.wons(0);

    @Column(name="price", nullable = false)
    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money amount cannot be negative");
        }
        this.amount = amount;
    }

    public static Money wons(int amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public static Money wons(long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }


    public Money add(Money money) {
        return new Money(this.amount.add(money.amount));
    }

    public Money multiply(int amount) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(amount)));
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Money other)) return false;
        return this.amount.compareTo(other.amount) == 0;
    }

    @Override
    public int hashCode() {
        return amount.stripTrailingZeros().hashCode();
    }
}
