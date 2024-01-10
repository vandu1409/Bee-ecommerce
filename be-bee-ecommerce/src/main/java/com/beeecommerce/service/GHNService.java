package com.beeecommerce.service;

import com.beeecommerce.entity.Address;
import com.beeecommerce.entity.Vendor;

import java.util.List;
import java.util.Map;

public interface GHNService {


    public Long calculateShippingFee(Long addressId , Long vendorsId, List<Long> productId);
}
