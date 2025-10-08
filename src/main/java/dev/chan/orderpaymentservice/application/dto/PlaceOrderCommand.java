package dev.chan.orderpaymentservice.application.dto;

import jakarta.validation.constraints.NotBlank;

public record PlaceOrderCommand(
        Long memberId,
        Long productId,
        int orderQuantity

) {
    public static PlaceOrderCommand of(@NotBlank Long memberId, @NotBlank Long productId, int orderQuantity) {
        return new PlaceOrderCommand(memberId,productId,orderQuantity);
    }
}