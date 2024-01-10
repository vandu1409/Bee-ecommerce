package com.beeecommerce.service;

import com.beeecommerce.entity.Category;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.CategoryDetailDto;
import com.beeecommerce.model.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


public interface CategoryService {
    Page<CategoryDetailDto> getCategories(Pageable pageable);

    CategoryDetailDto getCategory(Long id);

    void createCategory(CategoryDto categoryDto, MultipartFile image) throws ApplicationException;

    void updateCategory(CategoryDto categoryDto, Category oldCategory,MultipartFile image) throws EntityNotFoundException, ApplicationException;

    void deleteCategory(Long id);

    Optional<Category> getCategoryById(Long id);

    List<CategoryDto> getParentCategories(Long childrenId);

    List<CategoryDto> getTopCategory(int top);

    List<CategoryDto> getCategoryByLevel(Integer level);

    List<CategoryDto> getChildrenCategories(Long id) throws EntityNotFoundException;

    String findByCategory(Long id);

    List<CategoryDto> findCategoriesWithParentIdInProduct();

    List<CategoryDto> findCategoriesWithParentId(long parentId);
}
