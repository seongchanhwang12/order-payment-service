package dev.chan.orderpaymentservice.common;

import java.util.List;

public class Ensure {

    public static String nonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is Null or Blank");
        }
        return value;
    }

    public static <T> T nonNull(T obj, String fieldName) {
        if (obj == null) {
            throw new IllegalArgumentException(fieldName + " must not be null");
        }

        return obj;
    }

    public static <T>  List<T> notEmpty(List<T> collection, String fieldName) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be null or empty");
        }
        return collection;
    }
}
