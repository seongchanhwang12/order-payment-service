package dev.chan.orderpaymentservice.application.order.dto;

import dev.chan.orderpaymentservice.domain.common.Money;

public record OrderedProduct(
        long productId,
        String name,
        Money price,
        int quantity
) {}
