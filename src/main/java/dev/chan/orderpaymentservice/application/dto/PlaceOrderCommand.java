package dev.chan.orderpaymentservice.application.dto;

public record PlaceOrderCommand(
        long memberId,
        Long productId,
        int orderQuantity
) {}