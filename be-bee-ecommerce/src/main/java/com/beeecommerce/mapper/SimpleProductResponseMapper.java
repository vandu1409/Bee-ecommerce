package com.beeecommerce.mapper;

import com.beeecommerce.entity.Classify;
import com.beeecommerce.entity.ClassifyGroup;
import com.beeecommerce.entity.Product;
import com.beeecommerce.mapper.core.BaseMapper;
import com.beeecommerce.model.dto.ClassifyDto;
import com.beeecommerce.model.response.ProductDetailResponse;
import com.beeecommerce.model.response.SimpleProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleProductResponseMapper implements BaseMapper<Product, SimpleProductResponse> {

    private final BrandMapper brandMapper;

    @Override
    @Cacheable(value = "product-simp", key = "#product.id")
    public SimpleProductResponse apply(Product product) {

        Long rootPrice = product.getClassifies().stream()
                .map(Classify::getSellPrice)
                .min(Long::compareTo)
                .orElse(0L);

        return SimpleProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .poster(product.getPoster())
                .star(4.7)
                .inStock(500)
                .likedCount(99)
                .sold(10)
                .voteCount(1000)
                .rootPrice(rootPrice)
                .shopName(product.getVendors().getName())
                .classifies(convertListClassifyDto(product))
                .brand(brandMapper.apply(product.getBrand()))
                .build();
    }

    public List<ClassifyDto> convertListClassifyDto(Product product) {
        List<ClassifyDto> classifiesDto = new ArrayList<>();

        for (ClassifyGroup group : product.getClassifyGroups()) {

            for (Classify classify : group.getClassifies()) {

                ClassifyDto classifyDto = ClassifyDto.builder()
                        .classifyGroupId(group.getId())
                        .classifyId(classify.getId())
                        .classifyValue(classify.getValue())
                        .classifyGroupValue(group.getValue())
                        .price(classify.getSellPrice())
                        .inventory(classify.getInventory())
                        .quantity(classify.getQuantity())
                        .build();

                classifiesDto.add(classifyDto);
            }
        }

        return classifiesDto;
    }
}
