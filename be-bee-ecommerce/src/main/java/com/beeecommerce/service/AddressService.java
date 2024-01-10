package com.beeecommerce.service;

import com.beeecommerce.entity.Address;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.AddressDto;
import com.beeecommerce.model.dto.LocalityDto;
import com.beeecommerce.model.dto.WardDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {
    Page<AddressDto> getAll(Pageable pageable);

    void createAddress(AddressDto addressDto);

    void update(AddressDto addressDto) throws ParamInvalidException;

    void deleteAddress(Long id);

    Address getById(Long addressId) throws EntityNotFoundException;

    List<LocalityDto> getAllProvince();

    List<LocalityDto> getAllDistrict(Long provinceId);

    List<LocalityDto> getAllWard(Long districtId);

    WardDetailDto getWardDetail(Long wardId) throws EntityNotFoundException;

    AddressDto findById(Long id);
}
