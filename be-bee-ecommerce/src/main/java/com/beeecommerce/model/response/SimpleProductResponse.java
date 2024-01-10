package com.beeecommerce.model.response;

import com.beeecommerce.model.dto.AttributeDto;
import com.beeecommerce.model.dto.BrandDto;
import com.beeecommerce.model.dto.ClassifyDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Cái product này là cái product đơn giản, chỉ để hiển thị theo dạng list, không có nhiều thông tin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SimpleProductResponse implements Serializable {
    private Long id;

    private String name;

    private Long rootPrice = 100000L;

    private Integer sold = 10;

    private Integer likedCount = 15;

    private Integer voteCount = 15; // Luượt đánh giá

    private Double star = 4.7;  // Trung bình sao

    private Integer inStock = 1; // Còn hàng trong kho

    private String poster;

    private BrandDto brand;

    private String shopName;

    private List<ClassifyDto> classifies;

}
