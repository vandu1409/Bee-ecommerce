package com.beeecommerce.mapper;

import com.beeecommerce.entity.Category;
import com.beeecommerce.model.dto.CategoryDto;
import com.beeecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoryMapper implements Function<Category, CategoryDto> {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto apply(Category category) {
        return CategoryDto
                .builder()
                .id(category.getId())
                .level(category.getLevel())
                .name(category.getName())
                .parentId(category.getParentId())
                .posterUrl(category.getPosterUrl())
                .build();
    }

    public void fillHasChildren(List<CategoryDto> categoryDto) {

        List<Map<String, Long>> result = categoryRepository
                .fillHasChildren(
                        categoryDto.stream()
                                .map(CategoryDto::getId)
                                .toList()
                );

        for (CategoryDto dto : categoryDto) {
            for (Map<String, Long> map : result) {
                if (dto.getId().equals(map.get("id"))) {
                    dto.setHasChildren(map.get("childrenCount") > 0);
                    break;
                }
            }
        }

    }


}
