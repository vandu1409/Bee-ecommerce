package com.beeecommerce.model.dto;


import com.beeecommerce.model.core.SimpleAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable, SimpleAddress {

    private Long id;


    private Long userId;


    private String receiverName;


    private String receiverPhone;


    private String note;


    private Long wardId;

    private String street;

    private String detailsAddress;


}
