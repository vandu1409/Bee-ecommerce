package com.beeecommerce.model.dto;

import com.beeecommerce.entity.Feedback;
import com.beeecommerce.model.response.SimpleProductResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderDetailDto {
    private Long id;

    private Long quantity;

    private Long totalPrice;

    private Long classifyId;

    private Long orderId;

    private ClassifyDto classifyDto;

    private String productName;

    private String shopName;

    private Boolean hasFeedback;

}
