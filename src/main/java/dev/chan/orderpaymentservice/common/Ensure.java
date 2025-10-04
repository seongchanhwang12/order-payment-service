package dev.chan.orderpaymentservice.common;

public class Ensure {

    public static String nonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
