package dev.chan.orderpaymentservice.common;

public record Quantity(int value) {

    public Quantity{
        if(value < 0)
            throw new IllegalArgumentException("value must be greater than or equal to 0, but value is " + value);
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
