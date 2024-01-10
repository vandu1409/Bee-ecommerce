package com.beeecommerce.service.impl;

import com.beeecommerce.constance.TypeUpLoad;
import com.beeecommerce.entity.Category;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.mapper.CategoryDetailMapper;
import com.beeecommerce.mapper.CategoryMapper;
import com.beeecommerce.model.dto.CategoryDetailDto;
import com.beeecommerce.model.dto.CategoryDto;
import com.beeecommerce.repository.CategoryRepository;
import com.beeecommerce.service.AmazonClientService;
import com.beeecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final CategoryDetailMapper categoryDetailMapper;

    private final AmazonClientService amazonClientService;

    @Override
    public Page<CategoryDetailDto> getCategories(Pageable pageable) {
        return categoryRepository
                .findAll(pageable)
                .map(categoryDetailMapper);
    }

    @Override
    public CategoryDetailDto getCategory(Long id) {
        return categoryRepository
                .findById(id)
                .map(categoryDetailMapper)
                .orElse(null);
    }

    @Override
    public void createCategory(CategoryDto categoryDto, MultipartFile image) throws ApplicationException {

        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setLevel(categoryDto.getLevel());
        category.setParentId(categoryDto.getParentId());
        category.setPosterUrl(categoryDto.getPosterUrl());

        if (image != null) {
            String imageUrl = amazonClientService.uploadFile(image, TypeUpLoad.IMAGE);
            category.setPosterUrl(imageUrl);
        }

        categoryRepository.save(category);

    }

    @Override
    public void updateCategory(CategoryDto categoryDto, Category oldCategory,MultipartFile image) throws EntityNotFoundException, ApplicationException {

        if (categoryDto.getName() != null) {
            oldCategory.setName(categoryDto.getName());
        }
        if (categoryDto.getParentId() != null) {
            oldCategory.setParentId(categoryDto.getParentId());
        }
        if (categoryDto.getLevel() != null) {
            oldCategory.setLevel(categoryDto.getLevel());
        }
        if (image != null) {
            String imageUrl = amazonClientService.uploadFile(image, TypeUpLoad.IMAGE);
            oldCategory.setPosterUrl(imageUrl);
        }

        categoryRepository.save(oldCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<CategoryDto> getParentCategories(Long childrenId) {
        return categoryRepository
                .getParentCategories(childrenId)
                .stream()
                .map(categoryMapper)
                .toList();
    }

    @Override
    public List<CategoryDto> getTopCategory(int top) {
        return categoryRepository
                .getTopCategory(top)
                .stream()
                .map(categoryMapper)
                .toList();

    }

    @Override
    public List<CategoryDto> getCategoryByLevel(Integer level) {
        List<CategoryDto> categoryDtos = categoryRepository
                .getCategoryByLevel(level)
                .stream()
                .map(categoryMapper)
                .toList();

        categoryMapper.fillHasChildren(categoryDtos);

        return categoryDtos;
    }

    @Override
    public List<CategoryDto> getChildrenCategories(Long id) throws EntityNotFoundException {
        List<CategoryDto> result = categoryRepository.findByParentId(id)
                .stream()
                .map(categoryMapper)
                .toList();


        if (result.isEmpty()) throw new EntityNotFoundException("Can't find any children category!");

        categoryMapper.fillHasChildren(result);

        return result;

    }

    @Override
    public String findByCategory(Long id) {
        List<CategoryDto> list = getChildrenCategories(id);

        if (list != null) {
            StringBuilder categoryName = new StringBuilder();

            for (int i = 0; i < list.size(); i++) {
                categoryName.append(list.get(i).getName());

                if (i < list.size() - 1) {
                    categoryName.append(" > ");
                }
            }

            return categoryName.toString();
        }

        return "";
    }

    @Override
    public List<CategoryDto> findCategoriesWithParentIdInProduct() {
        List<CategoryDto> result = categoryRepository.findCategoriesWithParentIdInProduct()
                .stream()
                .map(categoryMapper)
                .toList();

        return result;
    }

    @Override
    public List<CategoryDto> findCategoriesWithParentId(long parentId) {
        List<CategoryDto> result = categoryRepository.findCategoriesWithParentId(parentId)
                .stream()
                .map(categoryMapper)
                .toList();

        return result;
    }

}
