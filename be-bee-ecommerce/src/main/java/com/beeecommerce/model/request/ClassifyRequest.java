package com.beeecommerce.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassifyRequest {

    private Long classifyId;

    private Long price;

    private Long inventory;

}
