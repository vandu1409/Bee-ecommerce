package com.beeecommerce.facade;

import com.beeecommerce.entity.Order;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.service.GHNService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GHNFacade {

    private final GHNService ghnService;


    public Long calculateShippingFee(Long addressId, Long vendorsId, List<Long> productId) throws ParamInvalidException {
        ObjectHelper.checkNullParam(addressId, vendorsId, productId);
        try {
            return ghnService.calculateShippingFee(addressId, vendorsId, productId);
        } catch (Exception e) {
            e.printStackTrace();
            return 37650L;
        }

    }


}
