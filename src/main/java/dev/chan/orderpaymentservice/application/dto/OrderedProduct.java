package dev.chan.orderpaymentservice.application.dto;

import dev.chan.orderpaymentservice.domain.common.Money;

public record OrderedProduct(
        long productId,
        String name,
        Money price,
        int quantity
) {}
