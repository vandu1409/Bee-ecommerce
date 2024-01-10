package com.beeecommerce.facade;

import com.beeecommerce.constance.ScopeVoucher;
import com.beeecommerce.entity.Order;
import com.beeecommerce.entity.Voucher;
import com.beeecommerce.entity.VoucherApplied;
import com.beeecommerce.exception.common.VoucherNotApplicableException;
import com.beeecommerce.service.VoucherAppliedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherAppliedFacade {

    private final VoucherAppliedService voucherAppliedService;

    public final List<VoucherApplied> appliedVoucher(List<Voucher> vouchers, Order order) throws VoucherNotApplicableException {


        // Check order đã được set orderDetail hay chưa
        if (order.getOrderDetails() == null || order.getOrderDetails().isEmpty()) {
            throw new VoucherNotApplicableException("Đơn hàng chưa có sản phẩm! Không thể áp dụng voucher!");
        }

        // Check order đã tính phí ship hay chưa
        if (order.getShippingFee() == null) {
            throw new VoucherNotApplicableException("Đơn hàng chưa tính phí ship! Không thể áp dụng voucher!");
        }

        // Nếu có n scope thì chắc chắn có n + 1 voucher sẽ lặp lại 1 scope
        if (vouchers.size() > ScopeVoucher.values().length) {
            throw new VoucherNotApplicableException("Không thể áp dụng 2 voucher cùng loại cho 1 đơn hàng!");
        }


        List<ScopeVoucher> scopeUsed = new ArrayList<>();
        for (Voucher voucher : vouchers) {


            // Check xem có voucher nào trùng scope hay không
            if (!scopeUsed.contains(voucher.getScope())) {
                scopeUsed.add(voucher.getScope());
            } else {
                throw new VoucherNotApplicableException("Không thể áp dụng 2 voucher cùng loại cho 1 đơn hàng!");
            }

            // Check hạng sử dụng voucher
                LocalDate now = LocalDate.now();
                if (now.isBefore(voucher.getStartDate()) || now.isAfter(voucher.getEndDate())) {
                    throw new VoucherNotApplicableException("Voucher không còn hạn sử dụng!");
                }

            // Check số lần sử dụng voucher
            if (voucher.getQuantity() <= 0) {
                throw new VoucherNotApplicableException("Voucher đã hết lượt sử dụng!");
            }

            // Check giá trị đơn hàng tối thiểu
            if (voucher.getMinApply() > order.getTotalPrice()) {
                throw new VoucherNotApplicableException("Chưa đạt giá trị đơn hàng tối thiểu để áp dụng!");
            }
        }

        return voucherAppliedService.appliedVoucher(vouchers, order);
    }

}
