package com.beeecommerce.service.impl;

import com.beeecommerce.constance.*;
import com.beeecommerce.entity.Account;
import com.beeecommerce.entity.Transaction;
import com.beeecommerce.exception.common.VNPayPaymentException;
import com.beeecommerce.mapper.TransactionMapper;
import com.beeecommerce.payment.IPNResponse;
import com.beeecommerce.payment.TxnRef;
import com.beeecommerce.payment.VNPayPaymentRequest;
import com.beeecommerce.payment.VNPayUtils;
import com.beeecommerce.repository.AccountRepository;
import com.beeecommerce.repository.OrderRepository;
import com.beeecommerce.repository.TransactionRepository;
import com.beeecommerce.service.AccountService;
import com.beeecommerce.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VNPayServiceImpl implements VNPayService {

    @Value("${app.payment.vnpay.secret-key}")
    private String vnpHashSecretKey;

    @Value("${app.payment.vnpay.tmn-code}")
    private String tmnCode;

    @Value("${app.payment.vnpay.returnUrl}")
    private String returnUrl;

    @Value("${app.payment.vnpay.expiresInMinutes}")
    private int expiresInMinutes;

    @Value("${app.payment.vnpay.payUrl}")
    private String payUrl;

    private final HttpServletRequest request;

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;

    private VNPayUtils init() {
        VNPayUtils vnpayUtils = new VNPayUtils(vnpHashSecretKey, tmnCode);
        vnpayUtils.setIpAddress(this.request);
        vnpayUtils.setReturnUrl(returnUrl);
        vnpayUtils.initTime(expiresInMinutes);
        vnpayUtils.setPayUrl(payUrl);
        return vnpayUtils;
    }

    @Override
    public VNPayUtils createPaymentUtils(VNPayPaymentRequest request) {

        if (!request.readyToPay()) {
            throw new VNPayPaymentException("This request is not ready to pay");
        }

        VNPayUtils vnpayUtils = init();

        vnpayUtils.setAmount(request.getFinalAmount());
        vnpayUtils.setOrderInfo(request.getOrderInfo());
        vnpayUtils.setOrderType(request.getOrderType());
        vnpayUtils.setTxnRef(request.getTxnRef());


        return vnpayUtils.validate();
    }

    @Override
    @Transactional
    public String requestDeposit(Long amount) {
        Account account = accountService.getAuthenticatedAccount();

        VNPayPaymentRequest transaction = transactionMapper.deposit(account, amount);

        return createPaymentUrl(transaction);
    }

    @Override
    public IPNResponse processPayment(Map<String, String> vnpayResponse) {

        String txnRefString = vnpayResponse.get(VNPayParameter.TXN_REF.getValue());

        TxnRef txnRef = TxnRef.fromString(txnRefString);

        // Check if txnRef is valid
        if (txnRef == null) {
            return new IPNResponse("Order Not Found", "01");
        }

        Optional<Transaction> transactionOtn = txnRef.getType() == TransactionType.DEPOSIT
                ? transactionRepository.findById(txnRef.getId())
                : transactionRepository.findByOrderId(txnRef.getId());

        // Check if transaction exists
        if (transactionOtn.isEmpty()) {
            return new IPNResponse("Order Not Found", "01");
        }

        Transaction transaction = transactionOtn.get();
        String resAmount = vnpayResponse.get(VNPayParameter.AMOUNT.getValue());

        // Check if amount is valid
        if (transaction.getAmount() * 100 != Long.parseLong(resAmount)) {
            return new IPNResponse("Invalid Amount", "04");
        }

        // Check if status is valid
        if (transaction.getStatus() != TransactionStatus.PENDING || transaction.getPaymentMethod() != PaymentMethod.VNPAY) {
            return new IPNResponse("Order already confirmed", "02");
        }

        // Check result transaction
        String getResponseCode = vnpayResponse.get(VNPayParameter.RESPONSE_CODE.getValue());
        if ("00".equals(getResponseCode)) {
            handleSuccessTransaction(transaction, txnRef);
        } else {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
        }

        return new IPNResponse("Confirm Success", "00");
    }

    private void handleSuccessTransaction(Transaction transaction, TxnRef txnRef) {
        switch (transaction.getType()) {
            case DEPOSIT:
                Account receiver = transaction.getReceiver();
                receiver.setBalance(receiver.getBalance() + transaction.getAmount());
                transaction.setStatus(TransactionStatus.SUCCESS);

                // Update transaction
                transactionRepository.save(transaction);

                // Update account
                accountRepository.save(receiver);
                break;
             case PAYORDER:
                log.error("TxnRef : {}, {}", txnRef.getId(), txnRef.getType());
                // Update order
                orderRepository.findById(txnRef.getId()).ifPresent(order -> {
                    order.setStatus(OrderStatus.PAID);
                    orderRepository.save(order);
                });
                log.error("Đã update order");
                transaction.setStatus(TransactionStatus.SUCCESS);

                // Update transaction
                transactionRepository.save(transaction);
                
                break;
            default:
                throw new VNPayPaymentException("Unknown transaction type");
        }
    }}
