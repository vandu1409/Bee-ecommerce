package com.beeecommerce.controller;

import com.beeecommerce.entity.Voucher;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.facade.VoucherFacade;
import com.beeecommerce.model.dto.VoucherDto;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.beeecommerce.constance.ApiPaths.VoucherPath.VOUCHER_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping(VOUCHER_PREFIX)
public class VoucherController {

    private final VoucherFacade voucherFacade;

    @GetMapping
    public ResponseObject<List<VoucherDto>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VoucherDto> voucherDtoPage = voucherFacade.getAll(pageable);
        return ResponseHandler.response(voucherDtoPage);
    }

    @PostMapping
    public ResponseObject<String> saveOrUpdateVoucher(@RequestBody VoucherDto voucherDto) throws ParamInvalidException, AuthenticateException {
        voucherFacade.create(voucherDto);
        return ResponseHandler.response("Lưu Thành Công");
    }

    @DeleteMapping("/{id}")
    public ResponseObject<String> deleteVoucherById(@PathVariable("id") Long id) throws ParamInvalidException {
        voucherFacade.deleteVoucherById(id);
        return ResponseHandler.response("Xóa Thành Công");
    }


    @GetMapping("/{id}")
    public ResponseObject<Optional<Voucher>> findById(@PathVariable("id") Long id) throws ParamInvalidException {
        Optional<Voucher> optionalVoucher = voucherFacade.findById(id);
        return ResponseHandler.response(optionalVoucher);
    }


}
