package com.beeecommerce.model.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class HashtagDto {

    private Long id;

    private String name;

}