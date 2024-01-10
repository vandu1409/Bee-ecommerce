package com.beeecommerce.facade;

import com.beeecommerce.entity.Voucher;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.VoucherDto;
import com.beeecommerce.service.VoucherService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoucherFacade {
    private final VoucherService voucherService;

    public Page<VoucherDto> getAll(Pageable pageable) {
        return voucherService.getAll(pageable);
    }

    public void create(VoucherDto voucherDto) throws ParamInvalidException, AuthenticateException {

        ObjectHelper.checkNullParam(voucherDto.getUserId(),
                voucherDto.getStartDate(), voucherDto.getEndDate(), voucherDto.getMinApply()
                , voucherDto.getMaxDiscount(), voucherDto.getType(), voucherDto.getScope(), voucherDto.getDiscount());

        if (voucherDto.getId() != null) {
            throw new IllegalArgumentException("Cannot create a voucher with id. Id found: " + voucherDto.getId());
        }

        voucherService.create(voucherDto);
    }

    public void deleteVoucherById(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);

        try {
            voucherService.deleteVoucher(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi xóa ", e);
        }
    }

    public Optional<Voucher> findById(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);

        return voucherService.findById(id);

    }


}

