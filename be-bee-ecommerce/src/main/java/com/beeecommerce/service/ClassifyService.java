package com.beeecommerce.service;

import com.beeecommerce.entity.Product;
import com.beeecommerce.model.dto.ClassifyDto;
import com.beeecommerce.model.dto.ClassifyNameDto;
import com.beeecommerce.model.response.ClassifyResponse;

import java.util.List;

public interface ClassifyService {

    void saveOrUpdate(List<ClassifyDto> classifies, ClassifyNameDto groupNameDto, ClassifyNameDto classifyNameDto, Product product);

    ClassifyResponse getClassify(Long id);
}
