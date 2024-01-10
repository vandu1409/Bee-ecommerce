package com.beeecommerce.model.dto;

import com.beeecommerce.constance.ScopeVoucher;
import com.beeecommerce.constance.TypeVoucher;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VoucherDto {

    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private TypeVoucher type;

    private Long discount;

    private Long maxDiscount;

    private ScopeVoucher scope;

    private Long minApply;

    private Integer quantity;

    private Long userId;
}
