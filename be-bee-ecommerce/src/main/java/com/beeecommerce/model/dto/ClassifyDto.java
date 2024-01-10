package com.beeecommerce.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassifyDto implements Serializable {

    private Long classifyId;

    private Long classifyGroupId;

    private String classifyGroupValue;

    private String classifyValue;

    private Long price;

    private Long inventory;

    private Long quantity;

    private String image;


}
