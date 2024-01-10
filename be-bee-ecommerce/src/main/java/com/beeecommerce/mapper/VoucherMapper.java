package com.beeecommerce.mapper;

import com.beeecommerce.entity.Voucher;
import com.beeecommerce.model.dto.VoucherDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class VoucherMapper implements Function<Voucher, VoucherDto> {

    @Override
    public VoucherDto apply(Voucher voucher) {
        return VoucherDto
                .builder()
                .id(voucher.getId())
                .startDate(voucher.getStartDate())
                .endDate(voucher.getEndDate())
                .type(voucher.getType())
                .discount(voucher.getDiscount())
                .maxDiscount(voucher.getMaxDiscount())
                .scope(voucher.getScope())
                .minApply(voucher.getMinApply())
                .quantity(voucher.getQuantity())
                .userId(voucher.getUser().getId())
                .build();
    }
}
