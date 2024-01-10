package com.beeecommerce.service;

import com.beeecommerce.entity.Voucher;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.model.dto.VoucherDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface VoucherService {
    Page<VoucherDto> getAll(Pageable pageable);

    void create(VoucherDto voucher) throws AuthenticateException;

    List<Voucher> getAllVoucherByIds(List<Long> ids);

    void deleteVoucher(Long id);

    Optional<Voucher> findById(Long id);
}
