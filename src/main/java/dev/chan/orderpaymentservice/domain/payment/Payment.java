package dev.chan.orderpaymentservice.domain.payment;

import jakarta.persistence.*;

@Entity
@Table(name = "PAYMENT")
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long orderId;
    private String idempotencyKey;
    private String pgTransactionId;
    private PaymentStatus status; // "PAID"|"FAILED"
    private String receiptUrl;

    public static Payment paid(long orderId, String key, String pgId, String receipt) {
        Payment p = new Payment();
        p.orderId = orderId;
        p.idempotencyKey = key;
        p.pgTransactionId = pgId;
        p.receiptUrl = receipt;
        p.status = PaymentStatus.PAID;
        return p;
    }
    public static Payment failed(long orderId, String key) {
        Payment p = new Payment();
        p.orderId = orderId;
        p.idempotencyKey = key;
        p.status = PaymentStatus.FAILED;
        return p;
    }
}
