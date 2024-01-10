package com.beeecommerce.model.response;


import com.beeecommerce.mapper.BrandMapper;
import com.beeecommerce.model.dto.AttributeDto;
import com.beeecommerce.model.dto.BrandDto;
import com.beeecommerce.model.dto.ClassifyDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductDetailResponse extends SimpleProductResponse {

    private List<ClassifyDto> classifies;

    private String classifyGroupName;

    private String classifyName;

    private List<String> images;

    private Long categoryId;

    private List<String> tags;

    private List<AttributeDto> attributes;

    private Long weight;

    private Long height;

    private Long length;

    private Long wight;

    private String description;

    List<FeedbackResponse> feedbacks;


}
