package com.beeecommerce.entity;

import com.beeecommerce.constance.PaymentMethod;
import com.beeecommerce.constance.TransactionStatus;
import com.beeecommerce.constance.TransactionType;
import com.beeecommerce.payment.TxnRef;
import com.beeecommerce.payment.VNPayPaymentRequest;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Builder
@Table(name = "\"transaction\"")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements VNPayPaymentRequest {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Account receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Account sender;

    @Column(name = "create_at")
    private Timestamp createAt = new Timestamp(System.currentTimeMillis());

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "order_id")
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 10, nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 10, nullable = false)
    private PaymentMethod paymentMethod;

    @Override
    public String getOrderInfo() {
        if (getType() == TransactionType.DEPOSIT) {
            return "Deposit to account " + getReceiver().getUsername();
        } else if (getType() == TransactionType.PAYORDER) {
            return "Pay order ";
        } else {
            return "Unknown transaction";
        }
    }

    private Long getPayId() {
        return getType() == TransactionType.PAYORDER ? getOrderId() : getId();
    }

    @Override
    public String getOrderType() {
        return getType().toString();
    }

    @Override
    public String getTxnRef() {
        return new TxnRef(getPayId(), getType(), "_").toString();
    }

    @Override
    public boolean readyToPay() {
        return getStatus() == TransactionStatus.PENDING
                && getPaymentMethod() == PaymentMethod.VNPAY
                && (getType() == TransactionType.DEPOSIT || getType()==TransactionType.PAYORDER);
    }
}