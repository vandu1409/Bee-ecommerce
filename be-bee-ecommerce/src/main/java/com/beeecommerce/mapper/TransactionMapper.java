package com.beeecommerce.mapper;

import com.beeecommerce.constance.PaymentMethod;
import com.beeecommerce.constance.TransactionStatus;
import com.beeecommerce.constance.TransactionType;
import com.beeecommerce.entity.Account;
import com.beeecommerce.entity.Transaction;
import com.beeecommerce.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class TransactionMapper {

    private final TransactionRepository transactionRepository;

    /**
     * Create a transaction for a payment save to database
     * @param account
     * @param amount
     * @return transaction saved
     */
    public Transaction deposit(Account account, Long amount) {
        Transaction transaction = transactionRepository
                .getDepositPending(account.getId())
                .orElse(
                        Transaction.builder()
                                .receiver(account)
                                .status(TransactionStatus.PENDING)
                                .type(TransactionType.DEPOSIT)
                                .createAt(new Timestamp(System.currentTimeMillis()))
                                .paymentMethod(PaymentMethod.VNPAY)
                                .build()
                );
        transaction.setAmount(amount);

        return transactionRepository.save(transaction);
    }

}
