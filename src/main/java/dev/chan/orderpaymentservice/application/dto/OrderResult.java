package dev.chan.orderpaymentservice.application.dto;

import dev.chan.orderpaymentservice.domain.Money;
import dev.chan.orderpaymentservice.domain.OrderProduct;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * ex)
 * {
 *     orderId : 1,
 *     orderedBy : "member1",
 *     orderedAt : 2025-01-01 11:22:33,
 *     totalPrice : 15000
 *     orderProducts : [
 *          {
 *              id: 1,
 *              name: "apple macbook pro2",
 *              price : 10000,
 *              quantity : 1
 *          },
 *          {
 *              id: 1,
 *              name: "apple macmini pro2",
 *              price : 5000,
 *              quantity : 1
 *          },
 *     ]
 * }
 *
 * @param orderId
 * @param orderedBy
 * @param orderedAt
 * @param totalAmount
 */
public record OrderResult(
        Long orderId,
        Long orderedBy,
        LocalDateTime orderedAt,
        Money totalAmount,
        List<OrderedProduct> orderProducts
){

}