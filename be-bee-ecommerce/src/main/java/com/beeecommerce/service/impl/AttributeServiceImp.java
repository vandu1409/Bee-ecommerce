package com.beeecommerce.service.impl;


import com.beeecommerce.entity.Attribute;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.mapper.AttributeMapper;
import com.beeecommerce.model.dto.AttributeDto;
import com.beeecommerce.model.dto.CategoryDto;
import com.beeecommerce.repository.AttributeRepository;
import com.beeecommerce.service.AttributeService;
import com.beeecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeServiceImp implements AttributeService {


    private final AttributeRepository attributeRepository;

    private final AttributeMapper attributeMapper;

    private final CategoryService categoryService;


    @Override
    public Page<AttributeDto> getAll(Pageable pageable) {
        return attributeRepository
                .findAll(pageable)
                .map(attributeMapper);
    }

    @Override
    public AttributeDto findById(Long id) {
        return attributeRepository
                .findById(id)
                .map(attributeMapper)
                .orElseThrow();
    }


    @Override
    public void createAttribute(AttributeDto attributeDto) {
        Attribute attribute = new Attribute();
        attribute.setName(attributeDto.getName());
        attributeRepository.save(attribute);
    }

    @Override
    @Transactional
    public void updateAttribute(Long id, String name) {
        attributeRepository.updateAttribute(name, id);
    }


    @Override
    public void deleteAttribute(Long id) {
        attributeRepository.deleteById(id);
    }


    @Override
    public List<AttributeDto> getRequireAttribute(Long categoryId) throws EntityNotFoundException {

        ArrayList<Long> categoryIds = new ArrayList<>(categoryService
                .getParentCategories(categoryId)
                .stream()
                .map(CategoryDto::getId)
                .toList());

        categoryIds.add(categoryId);

        return attributeRepository
                .getRequireAttribute(categoryIds)
                .stream()
                .map(attributeMapper)
                .toList();
    }

    @Override
    public List<AttributeDto> searchAttribute(String key) {
        return attributeRepository
                .getAllByNameContaining(key)
                .stream()
                .map(attributeMapper)
                .toList();
    }

}
