package com.beeecommerce.model.dto;

import com.beeecommerce.model.core.Locality;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocalityDto implements Locality {

    private Long id;

    private String name;

    private String code;

}
