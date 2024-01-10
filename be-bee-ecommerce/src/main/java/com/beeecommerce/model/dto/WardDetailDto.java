package com.beeecommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WardDetailDto {

    LocalityDto province;

    LocalityDto district;

    LocalityDto ward;


}
