package dev.chan.orderpaymentservice.domain.common;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Embeddable
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Quantity {

    @Column(name="quantity", nullable = false)
    private final int value;

    public Quantity(int value){
        if(value < 0){
            throw new IllegalArgumentException("value must be greater than or equal to 0, but value is " + value);
        }
        this.value = value;
    }

    public static Quantity of(int value){
        return new Quantity(value);
    }

    public Quantity subtract(Quantity other) {
        if(!canSubtract(other)){
            throw new IllegalArgumentException("Cannot subtract " + other + " from " + this);
        }

        int result = this.value - other.value;
        return new Quantity(result);
    }

    public boolean canSubtract(Quantity other) {
        return this.value >= other.value;
    }

    public boolean isLessThan(Quantity orderQuantity) {
        return this.value < orderQuantity.value;
    }
}
