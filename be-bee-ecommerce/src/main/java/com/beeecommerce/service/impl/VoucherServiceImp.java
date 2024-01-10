package com.beeecommerce.service.impl;

import com.beeecommerce.constance.ScopeVoucher;
import com.beeecommerce.constance.TypeVoucher;
import com.beeecommerce.entity.Account;
import com.beeecommerce.entity.Order;
import com.beeecommerce.entity.Voucher;
import com.beeecommerce.entity.VoucherApplied;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.common.VoucherNotApplicableException;
import com.beeecommerce.mapper.VoucherMapper;
import com.beeecommerce.model.dto.VoucherDto;
import com.beeecommerce.repository.VoucherRepository;
import com.beeecommerce.repository.VoucherAppliedRepository;
import com.beeecommerce.service.AccountService;
import com.beeecommerce.service.VoucherAppliedService;
import com.beeecommerce.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoucherServiceImp implements VoucherService, VoucherAppliedService {

    private final VoucherRepository voucherRepository;

    private final VoucherMapper voucherMapper;

    private final AccountService accountService;

    private final VoucherAppliedRepository voucherAppliedRepository;

    @Override
    public Page<VoucherDto> getAll(Pageable pageable) {
        return voucherRepository.findAll(pageable).map(voucherMapper);
    }

    @Override
    public void create(VoucherDto voucherDto) throws AuthenticateException {

        Voucher voucher = new Voucher();
        BeanUtils.copyProperties(voucherDto, voucher);

        Account account = accountService.getAuthenticatedAccount();

        voucher.setUser(account);

        voucherRepository.save(voucher);

    }

    @Override
    public List<Voucher> getAllVoucherByIds(List<Long> ids) {
        return voucherRepository.findAllById(ids);
    }


    @Override
    public void deleteVoucher(Long id) {
        voucherRepository.deleteById(id);
    }

    @Override
    public Optional<Voucher> findById(Long id) {
        return voucherRepository.findById(id);
    }


    @Override
//    @Transactional
    public List<VoucherApplied> appliedVoucher(List<Voucher> vouchers, Order order) throws VoucherNotApplicableException {


        List<VoucherApplied> vouchersApplied = new ArrayList<>();


        for (Voucher voucher : vouchers) {

            VoucherApplied voucherApplied = new VoucherApplied();
            voucherApplied.setVoucher(voucher);
            voucher.setQuantity(voucher.getQuantity() - 1);
            voucherApplied.setOrder(order);


            // Nếu voucher là loại giảm giá trực tiếp
            if (voucher.getType() == TypeVoucher.AMOUNT) {

                // Giảm giá trực tiếp tức là lấy mức giảm giá tối đa

                // Lưu số tiền giảm giá
                voucherApplied.setDiscount(
                        voucher.getMaxDiscount() > order.getTotalPrice()
                                ? order.getTotalPrice()
                                : voucher.getMaxDiscount()
                );


                // Giảm giá theo phần trăm
            } else if (voucher.getType() == TypeVoucher.PERCENT) {

                // Kiểm tra xem giảm vào tiền ship hay không
                if (voucher.getScope() == ScopeVoucher.SHIPPING) {
                    // Giảm giá theo phần trăm của tiền ship
                    long discountValue = order.getShippingFee() * voucher.getMaxDiscount() / 100;

                    voucherApplied.setDiscount(
                            discountValue > order.getShippingFee()
                                    ? order.getShippingFee()
                                    : discountValue
                    );

                } else {
                    // Giảm giá theo phần trăm của tổng giá trị đơn hàng
                    long discountValue = order.getTotalPrice() * voucher.getMaxDiscount() / 100;

                    // Đưa mã số tiền giảm giá vào map
                    voucherApplied.setDiscount(
                            discountValue > order.getTotalPrice()
                                    ? voucher.getMaxDiscount()
                                    : discountValue
                    );
                }

            }

            vouchersApplied.add(voucherApplied);
        }

        order.setVouchersApplied(vouchersApplied);

//        voucherAppliedRepository.saveAll(vouchersApplied);

        return vouchersApplied;
    }
}
