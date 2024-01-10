package com.beeecommerce.controller;

import com.beeecommerce.entity.Address;
import com.beeecommerce.entity.Classify;
import com.beeecommerce.entity.Vendor;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.facade.GHNFacade;
import com.beeecommerce.model.dto.CartDto;
import com.beeecommerce.model.request.ShippingFeeRequest;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import com.beeecommerce.repository.ClassifyRepository;
import com.beeecommerce.service.GHNService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.BrandPath.BRAND_PREFIX;
import static com.beeecommerce.constance.ApiPaths.GHNPath.GHN_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping(GHN_PREFIX)
public class GHNController {
    private final GHNFacade ghnFacade;


    private final ClassifyRepository classifyRepository;
    @PostMapping("/calculate-shipping-fee")
    public ResponseObject<Long> calculateShippingFee(@RequestBody ShippingFeeRequest request)
            throws ParamInvalidException {

        List<Long> ids= classifyRepository.findAllById(request.getClassifyId())
                .stream().map(i -> i.getGroup().getProduct().getId()).toList();

        Long shippingFee = ghnFacade.calculateShippingFee(request.getAddress(), request.getVendorIds(), ids);
        return ResponseHandler.response(shippingFee);
    }
}
