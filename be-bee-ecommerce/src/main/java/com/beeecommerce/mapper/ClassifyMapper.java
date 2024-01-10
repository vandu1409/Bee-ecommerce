package com.beeecommerce.mapper;

import com.beeecommerce.entity.Classify;
import com.beeecommerce.mapper.core.BaseMapper;
import com.beeecommerce.model.dto.ClassifyDto;
import com.beeecommerce.model.response.ClassifyResponse;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ClassifyMapper implements BaseMapper<Classify, ClassifyDto> {
    @Override
    public ClassifyDto apply(Classify classify) {
       return ClassifyDto.builder()
                .classifyGroupId(classify.getGroup().getId())
                .classifyGroupValue(classify.getGroup().getValue())
                .classifyId(classify.getId())
                .classifyValue(classify.getValue())
                .price(classify.getSellPrice())
                .inventory(classify.getInventory())
                .quantity(classify.getQuantity())
               .image(classify.getGroup().getProduct().getProductImages().get(0).getUrl())
                .build();
    }


    public ClassifyResponse applyClassifyResponse(Classify classify) {
        return ClassifyResponse.builder()
                .classifyGroupId(classify.getGroup().getId())
                .classifyGroupValue(classify.getGroup().getValue())
                .classifyId(classify.getId())
                .classifyValue(classify.getValue())
                .price(classify.getSellPrice())
                .inventory(classify.getInventory())
                .quantity(classify.getQuantity())
                .classifyName(classify.getClassifyName().getName())
                .classifyGroupName(classify.getGroup().getClassifyName().getName())
                .build();
    }
}
