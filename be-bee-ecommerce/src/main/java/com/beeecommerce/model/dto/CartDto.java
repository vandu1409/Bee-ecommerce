package com.beeecommerce.model.dto;


import com.beeecommerce.entity.Classify;
import com.beeecommerce.model.response.SimpleProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {
    private Long id;

    private Long quantity;

    private Long classifyId;

    private ClassifyDto classify;

    private SimpleProductResponse product;

    private String vendorName;

    private Long vendorId;

}
