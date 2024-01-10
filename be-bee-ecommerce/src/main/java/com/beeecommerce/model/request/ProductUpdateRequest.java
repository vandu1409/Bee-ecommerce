package com.beeecommerce.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
    private Long id;

    private List<ClassifyRequest> classifyRequests;
}
