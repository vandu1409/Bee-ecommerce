package com.beeecommerce.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    private Long vendorId;

    private String name;

    private Long price;

    @JsonIgnore
    private List<MultipartFile> files;

    private List<String> fileUrls;

    private List<Long> deletedImageIds;

    private List<Long> deletedGroupIds;

    private List<Long> deletedClassifyIds;

    private Long categoryId;

    private Long brandId;

    private List<AttributeDto> attributes;

    private List<ClassifyDto> classifies;

    private ClassifyNameDto classifyGroupName;

    private ClassifyNameDto classifyName;

    private List<String> tags;

    private Long weight;

    private Long height;

    private Long length;

    private Long wight;

    private String description;
}
