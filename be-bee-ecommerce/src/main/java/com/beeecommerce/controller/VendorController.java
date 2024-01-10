package com.beeecommerce.controller;

import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.facade.VendorFacade;
import com.beeecommerce.model.dto.VendorDto;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.VendorPath.VENDOR_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping(VENDOR_PREFIX)
public class VendorController {

    private final VendorFacade vendorFacade;

    @GetMapping()
    public ResponseObject<List<VendorDto>> getVendor(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<VendorDto> vendors = vendorFacade.getVendors(pageable);

        return ResponseHandler.response(vendors);
    }

    @PostMapping()
    public ResponseObject<?> insertVendor(@RequestBody VendorDto vendorDto) throws AuthenticateException {

        vendorFacade.insert(vendorDto);

        return ResponseHandler.message("Đăng ký thành công");
    }

    @PutMapping({"/{id}"})
    public ResponseObject<String> updateVendor(@PathVariable Long id, VendorDto vendorDto) throws ApplicationException {

        vendorFacade.update(id, vendorDto);

        return ResponseHandler.response("Cập nhật thành công thông tin người bán");
    }

    @DeleteMapping({"/{id}"})
    public ResponseObject<String> deleteVendor(@PathVariable Long id) throws ParamInvalidException {

        vendorFacade.delete(id);

        return ResponseHandler.response("Xóa thành công người bán");
    }

    @GetMapping("/{id}")
    public ResponseObject<VendorDto> getVendor(@PathVariable Long id) throws ParamInvalidException {
        return ResponseHandler.response(vendorFacade.getVendor(id));
    }
}
