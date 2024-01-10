package com.beeecommerce.service;

import com.beeecommerce.exception.auth.AuthenticateException;

public interface StatisticalService {
    double statisticalRevenueByCurrentMonth() throws AuthenticateException;

    int totalOrderByCurrentMoth() throws AuthenticateException;
}
