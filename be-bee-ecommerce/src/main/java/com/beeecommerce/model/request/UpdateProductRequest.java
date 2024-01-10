package com.beeecommerce.model.request;

import com.beeecommerce.model.dto.AttributeDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {

    private Long productId;
    private String classifyGroupName;

    private String classifyName;

    private String name;

    private Long brandId;

    private Long categoryId;

    private String description;

    private List<String> tags;

    private List<AttributeDto> attributes;

    private Long weight;

    private Long height;

    private Long length;

    private Long wight;



}
