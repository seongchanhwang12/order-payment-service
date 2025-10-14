package dev.chan.orderpaymentservice.application.order.dto;

import java.math.BigDecimal;

public record OrderLine(
        long orderProductId,
        long productId,
        String productName,
        int quantity,
        BigDecimal productPrice,
        BigDecimal totalPriceAmount

) {
    public static OrderLine of(
            long orderProductId,
            long productId,
            String productName,
            int quantity,
            BigDecimal productPrice,
            BigDecimal totalPriceAmount) {
        return new OrderLine(
                orderProductId,
                productId,
                productName,
                quantity,
                productPrice,
                totalPriceAmount);
    }

}
