package com.beeecommerce.mapper;

import com.beeecommerce.entity.Attribute;
import com.beeecommerce.mapper.core.BaseMapper;
import com.beeecommerce.model.dto.AttributeDto;
import com.beeecommerce.repository.AttributeRepository;
import org.springframework.stereotype.Service;

@Service
public class AttributeMapper implements BaseMapper<Attribute, AttributeDto> {

    private final AttributeRepository attributeRepository;

    public AttributeMapper(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    @Override
    public AttributeDto apply(Attribute attribute) {
        return AttributeDto
                .builder()
                .id(attribute.getId())
                .name(attribute.getName())
                .build();
    }

    @Override
    public Attribute reverse(AttributeDto attributeDto) {
        return attributeRepository
                .findFirstByName(attributeDto.getName())
                .orElse(attributeRepository
                        .save(Attribute.builder()
                                .name(attributeDto.getName())
                                .build()));


    }
}
