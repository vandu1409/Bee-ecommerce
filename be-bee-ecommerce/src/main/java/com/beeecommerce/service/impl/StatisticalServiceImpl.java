package com.beeecommerce.service.impl;

import com.beeecommerce.entity.Account;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.repository.OrderRepository;
import com.beeecommerce.service.AccountService;
import com.beeecommerce.service.StatisticalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticalServiceImpl implements StatisticalService {

    private final AccountService accountService;

    private final OrderRepository orderRepository;

    @Override
    public double statisticalRevenueByCurrentMonth() throws AuthenticateException {
        Account account = accountService.getAuthenticatedAccount();

        return orderRepository.statisticalRevenueByCurrentMonth(account.getId());
    }

    @Override
    public int totalOrderByCurrentMoth() throws AuthenticateException {
        Account account = accountService.getAuthenticatedAccount();

        return orderRepository.totalOrderByCurrentMoth(account.getId());
    }
}
