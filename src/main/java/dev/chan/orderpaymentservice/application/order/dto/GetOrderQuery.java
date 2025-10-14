package dev.chan.orderpaymentservice.application.order.dto;

public record GetOrderQuery(long orderId) {

    public GetOrderQuery{
        if(orderId <= 0){
            throw new IllegalArgumentException("orderId must be greater than 0");
        }
    }

}
