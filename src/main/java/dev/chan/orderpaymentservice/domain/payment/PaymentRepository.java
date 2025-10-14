package dev.chan.orderpaymentservice.domain.payment;

public interface PaymentRepository {
    void save(Payment payment);
}
