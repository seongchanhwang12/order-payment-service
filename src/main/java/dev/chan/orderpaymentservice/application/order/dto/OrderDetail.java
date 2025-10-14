package dev.chan.orderpaymentservice.application.order.dto;

import java.util.List;

public record OrderDetail(OrderHeader orderHeader, List<OrderLine> orderLines) {

}
