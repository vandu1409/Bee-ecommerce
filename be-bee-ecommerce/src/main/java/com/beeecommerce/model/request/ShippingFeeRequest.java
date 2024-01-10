package com.beeecommerce.model.request;

import com.beeecommerce.entity.Address;
import com.beeecommerce.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingFeeRequest {
    private Long address;
    private Long vendorIds;
    private List<Long> productId;

    private List<Long> classifyId;
}
