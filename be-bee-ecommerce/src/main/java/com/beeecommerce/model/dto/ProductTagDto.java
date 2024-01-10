package com.beeecommerce.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductTagDto {

    private Long id;

    private Long productId;

    private HashtagDto hashtagDto;


}