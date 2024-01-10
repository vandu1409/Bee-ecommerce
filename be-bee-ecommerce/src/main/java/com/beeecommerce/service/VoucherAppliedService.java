package com.beeecommerce.service;

import com.beeecommerce.entity.Order;
import com.beeecommerce.entity.Voucher;
import com.beeecommerce.entity.VoucherApplied;
import com.beeecommerce.exception.common.VoucherNotApplicableException;

import java.util.List;

public interface VoucherAppliedService {

    List<VoucherApplied> appliedVoucher(List<Voucher> vouchers, Order order) throws VoucherNotApplicableException;

}
