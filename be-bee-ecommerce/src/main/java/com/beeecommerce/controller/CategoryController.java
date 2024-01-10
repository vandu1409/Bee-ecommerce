package com.beeecommerce.controller;

import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.facade.CategoryFacade;
import com.beeecommerce.model.dto.CategoryDetailDto;
import com.beeecommerce.model.dto.CategoryDto;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.CategoryPath.*;

@RestController
@RequestMapping(CATEGORY_PREFIX)
@RequiredArgsConstructor
@Tag(name = "Category Controller")
public class CategoryController {

    private final CategoryFacade categoryFacade;

    private final ObjectMapper objectMapper;

    @GetMapping
//    @Cacheable(value = "categories")
    public ResponseObject<List<CategoryDetailDto>> getCategory(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<CategoryDetailDto> categories = categoryFacade.getCategories(pageable);

        return ResponseHandler.response(categories);
    }


    @GetMapping("{id}")
    public ResponseObject<CategoryDetailDto> getCategoryById(
            @PathVariable Long id
    ) throws ApplicationException {

        CategoryDetailDto category = categoryFacade.getCategory(id);

        return ResponseHandler.response(category);
    }


    @PostMapping
    @CacheEvict(value = "categories", allEntries = true)
    public ResponseObject<String> createCategory(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "parentId", required = false)Long parentId,
            @RequestPart(name = "image", required = false)MultipartFile image
            ) throws ApplicationException, JsonProcessingException {

        CategoryDto categoryDto = CategoryDto.builder()
                .name(name)
                .parentId(parentId)
                .build();

        categoryFacade.createCategory(categoryDto,image);

        return ResponseHandler.response("Create category successfully!");
    }

    @PutMapping("{id}")
    @CacheEvict(value = "categories", allEntries = true)
    public ResponseObject<String> updateCategory(
            @PathVariable Long id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "parentId", required = false , defaultValue = "-1")Long parentId,
            @RequestPart(name = "image", required = false)MultipartFile image
    ) throws EntityNotFoundException ,ApplicationException{


        CategoryDto categoryDto = CategoryDto.builder()
                .name(name)
                .parentId(parentId)
                .build();

        categoryFacade.updateCategory(id, categoryDto,image);

        return ResponseHandler.response("Update category successfully!");
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "categories", allEntries = true)
    public ResponseObject<String> deleteCategory(
            @PathVariable Long id
    ) {

        categoryFacade.deleteCategory(id);

        return ResponseHandler.response("Delete category successfully!");
    }

    @GetMapping(CATEGORY_LEVEL)
    public ResponseObject<List<CategoryDto>> getCategoryByLevel(
            @PathVariable Integer level) {

        List<CategoryDto> categories = categoryFacade.getCategoryByLevel(level);

        return ResponseHandler.response(categories);
    }

    @GetMapping(CATEGORY_CHILDREN)
    public ResponseObject<List<CategoryDto>> getChildrenCategories(
            @PathVariable Long id) throws EntityNotFoundException {

        List<CategoryDto> categories = categoryFacade.getChildrenCategories(id);

        return ResponseHandler.response(categories);
    }

    @GetMapping("/string/{id}")
    public ResponseObject<?> findByCategory(@PathVariable("id")Long id) throws ParamInvalidException {
        return ResponseHandler.response(categoryFacade.findByCategory(id));
    }

    @GetMapping("home/allCategory")
    public ResponseObject<List<CategoryDto>> findCategoriesWithParentIdInProduct() {
        List<CategoryDto> categories = categoryFacade.findCategoriesWithParentIdInProduct();
        return ResponseHandler.response(categories);
    }

    @GetMapping("home/allCategory/{id}")
    public ResponseObject<List<CategoryDto>> findCategoriesWithParentId(@PathVariable Long id) {
        List<CategoryDto> categories = categoryFacade.findCategoriesWithParentId(id);
        return ResponseHandler.response(categories);
    }

}
