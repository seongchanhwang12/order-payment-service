package dev.chan.orderpaymentservice.repository.payment;

import dev.chan.orderpaymentservice.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {
}
