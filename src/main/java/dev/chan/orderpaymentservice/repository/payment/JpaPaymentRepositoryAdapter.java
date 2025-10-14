package dev.chan.orderpaymentservice.repository.payment;

import dev.chan.orderpaymentservice.domain.payment.Payment;
import dev.chan.orderpaymentservice.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaPaymentRepositoryAdapter implements PaymentRepository {

    private final JpaPaymentRepository paymentRepository;

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

}
