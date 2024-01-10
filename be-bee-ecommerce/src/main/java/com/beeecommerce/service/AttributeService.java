package com.beeecommerce.service;

import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.model.dto.AttributeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttributeService {

    Page<AttributeDto> getAll(Pageable pageable);

    void createAttribute(AttributeDto name);

    void updateAttribute(Long id, String name);

    void deleteAttribute(Long id);

    AttributeDto findById(Long id);

    List<AttributeDto> getRequireAttribute(Long categoryId) throws EntityNotFoundException;

    List<AttributeDto> searchAttribute(String key);
}
