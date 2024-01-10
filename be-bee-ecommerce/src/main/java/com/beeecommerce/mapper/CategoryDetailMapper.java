package com.beeecommerce.mapper;

import com.beeecommerce.entity.Category;
import com.beeecommerce.model.dto.CategoryDetailDto;
import com.beeecommerce.model.dto.CategoryDto;
import com.beeecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoryDetailMapper implements Function<Category, CategoryDetailDto> {

    private final CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;


    public List<CategoryDto> getParentCategories(Long childrenId) {

        return categoryRepository
                .getParentCategories(childrenId)
                .stream()
                .map(categoryMapper)
                .toList();
    }


    @Override
    public CategoryDetailDto apply(Category category) {

        CategoryDetailDto categoryDetailDto = new CategoryDetailDto(category);

        categoryDetailDto.setParentCategories(
                getParentCategories(categoryDetailDto.getId())
        );

        return categoryDetailDto;
    }
}
