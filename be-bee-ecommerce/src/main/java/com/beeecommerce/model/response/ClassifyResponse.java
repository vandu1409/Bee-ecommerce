package com.beeecommerce.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassifyResponse {
    private Long classifyId;

    private Long classifyGroupId;

    private String classifyGroupValue;

    private String classifyValue;

    private String classifyName;

    private String classifyGroupName;

    private Long price;

    private Long inventory;

    private Long quantity;
}
