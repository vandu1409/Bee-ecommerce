package com.beeecommerce.controller;

import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.facade.AddressFacade;
import com.beeecommerce.model.dto.AddressDto;
import com.beeecommerce.model.dto.LocalityDto;
import com.beeecommerce.model.dto.OrderDto;
import com.beeecommerce.model.dto.WardDetailDto;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import com.beeecommerce.repository.DistrictRepository;
import com.beeecommerce.repository.WardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.AddressPath.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADDRESS_PREFIX)
public class AddressController {
    private final AddressFacade addressFacade;
    private final WardRepository wardRepository;
    private final DistrictRepository districtRepository;


    @GetMapping()
    public ResponseObject<List<AddressDto>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size) {


        Pageable pageable = Pageable.ofSize(size).withPage(page);

        return ResponseHandler.response(addressFacade.getAll(pageable));
    }

    @PostMapping()
    public ResponseObject<String> create(@RequestBody AddressDto addressDto) throws ParamInvalidException {
        addressFacade.createAddress(addressDto);
        return ResponseHandler.response("Lưu Thành Công");
    }

    @DeleteMapping("{id}")
    public ResponseObject<String> delete(@PathVariable("id") Long id) throws ParamInvalidException {
        addressFacade.delete(id);
        return ResponseHandler.response("Xóa Thành Công");

    }

    @PutMapping("/{id}")
    public ResponseObject<String> update(@PathVariable Long id, @RequestBody AddressDto addressDto) throws ParamInvalidException {
        addressFacade.update(id, addressDto);
        return ResponseHandler.response("Update Thành Công");
    }

    @GetMapping(ADDRESS_PROVINCE)
    public ResponseObject<List<LocalityDto>> getAllProvince() {
        List<LocalityDto> results = addressFacade.getAllProvince();
        return ResponseHandler.response(results, null);
    }

    @GetMapping(ADDRESS_DISTRICT)
    public ResponseObject<List<LocalityDto>> getAllDistrict(@PathVariable("provinceId") Long provinceId) {
        List<LocalityDto> results = addressFacade.getAllDistrict(provinceId);
        return ResponseHandler.response(results, null);
    }

    @GetMapping(ADDRESS_WARD)
    public ResponseObject<List<LocalityDto>> getAllWard(@PathVariable("districtId") Long districtId) {
        List<LocalityDto> results = addressFacade.getAllWard(districtId);
        return ResponseHandler.response(results, null);
    }

    @GetMapping(WARD_DETAIL)
    public ResponseObject<WardDetailDto> getWardDetail(@PathVariable("wardId") Long wardId) throws EntityNotFoundException {
        WardDetailDto results = addressFacade.getWardDetail(wardId);
        return ResponseHandler.response(results);
    }

    @GetMapping("{id}")
    public ResponseObject<AddressDto> findById(@PathVariable("id") Long id) throws ParamInvalidException {
        return ResponseHandler.response(addressFacade.findById(id));
    }
}


