package com.beeecommerce.model.dto;

import com.beeecommerce.entity.Category;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CategoryDetailDto extends CategoryDto implements Serializable {

    private List<CategoryDto> parentCategories;

    public CategoryDetailDto(Category category) {

        super(
                category.getId(),
                category.getParentId(),
                category.getLevel(),
                category.getName(),
                null,
                category.getPosterUrl()
        );
    }
}
