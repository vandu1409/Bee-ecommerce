package com.beeecommerce.mapper;

import com.beeecommerce.entity.Vendor;
import com.beeecommerce.model.dto.VendorDto;
import com.beeecommerce.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class VendorMapper implements Function<Vendor, VendorDto> {

    private final VendorRepository vendorRepository;

    public VendorMapper(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }


    @Override
    public VendorDto apply(Vendor vendor) {
        if (vendor == null) {
            return null;
        }
        return VendorDto.builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .registerAt(vendor.getRegisterAt())
                .userId(vendor.getUser().getId())
                .street(vendor.getAddress().getStreet())
                .fullAddress(vendor.getAddress().getStringAddress())
                .addressNote(vendor.getAddress().getNote())
                .wardId(vendor.getAddress().getWard().getId())
                .receiverPhone(vendor.getUser().getPhoneNumber())
                .image(vendor.getUser().getAvatar())
                .province(vendor.getAddress().getWard().getDistrict().getProvince().getName())
                .productCount(vendorRepository.getProductCount(vendor.getId()))
                .build();
    }
}
