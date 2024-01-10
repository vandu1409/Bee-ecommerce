package com.beeecommerce.facade;

import com.beeecommerce.entity.Category;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.CategoryDetailDto;
import com.beeecommerce.model.dto.CategoryDto;
import com.beeecommerce.service.CategoryService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryFacade {

    private final CategoryService categoryService;


    private void validateCategory(CategoryDto categoryDto) throws ParamInvalidException {

        if (categoryDto.getName() == null) {
            throw new ParamInvalidException(" \"name\" is required!");
        }

//        if (categoryDto.getPosterUrl() == null) {
//            throw new ParamInvalidException("\"posterUrl\" is required!");
//        }

        checkLevel(categoryDto);

    }

    private void checkLevel(CategoryDto categoryDto) {
        Long parentId = categoryDto.getParentId();

        if (parentId != null && parentId != -1) {
            Integer parentLevel = categoryIsExisted(parentId).getLevel();
            categoryDto.setLevel(parentLevel + 1);
        } else {
            categoryDto.setLevel(1);
        }
    }

    private Category categoryIsExisted(Long id) {
        return categoryService
                .getCategoryById(id)
                .orElseThrow(
                        () -> new RuntimeException("Category is not existed!")
                );
    }


    public Page<CategoryDetailDto> getCategories(Pageable pageable) {
        return categoryService.getCategories(pageable);
    }

    public CategoryDetailDto getCategory(Long id) throws ApplicationException {

        if (id == null) {
            throw new ParamInvalidException("Id is required!");
        }


        return categoryService.getCategory(id);


    }

    public void createCategory(CategoryDto categoryDto, MultipartFile image) throws ApplicationException {

        validateCategory(categoryDto);

        categoryService.createCategory(categoryDto,image);
    }

    public void updateCategory(Long id, CategoryDto categoryDto,MultipartFile image) throws EntityNotFoundException,ApplicationException {

        checkLevel(categoryDto);

        Category category = categoryIsExisted(id);

        categoryService.updateCategory(categoryDto, category,image);
    }

    public void deleteCategory(Long id) {

        categoryIsExisted(id);

        categoryService.deleteCategory(id);
    }

    public List<CategoryDto> getCategoryByLevel(Integer level) {
        return categoryService.getCategoryByLevel(level);
    }

    public List<CategoryDto> getChildrenCategories(Long id) throws EntityNotFoundException {
        return categoryService.getChildrenCategories(id);
    }

    public String findByCategory(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);

        return categoryService.findByCategory(id);
    }

    public List<CategoryDto> findCategoriesWithParentIdInProduct() {
        return categoryService.findCategoriesWithParentIdInProduct();
    }

    public List<CategoryDto> findCategoriesWithParentId(Long parentId) {
        return categoryService.findCategoriesWithParentId(parentId);
    }
}
