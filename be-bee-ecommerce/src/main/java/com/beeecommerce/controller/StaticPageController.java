package com.beeecommerce.controller;

import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.facade.StaticPageFacade;
import com.beeecommerce.model.dto.StaticPageDto;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.StaticPagePath.STATIC_PAGE_PREFIX;


@RestController
@RequiredArgsConstructor
@RequestMapping(STATIC_PAGE_PREFIX)
public class StaticPageController {
    private final StaticPageFacade staticPageFacade;

    @GetMapping
    @Operation(summary = "Get all static page", description = "Static page này sẽ không có content")
    public ResponseObject<List<StaticPageDto>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StaticPageDto> staticPageDtos = staticPageFacade.getAll(pageable);
        return ResponseHandler.response(staticPageDtos);
    }

    @PostMapping
    public ResponseObject<?> createOrUpdate(@RequestBody StaticPageDto staticPageDto) throws ParamInvalidException {
        staticPageFacade.create(staticPageDto);
        return ResponseHandler.message("Tạo thành công trang [%s]".formatted(staticPageDto.getTitle()));
    }

    @PutMapping("{id}")
    public ResponseObject<?> update(@PathVariable Long id, @RequestBody StaticPageDto staticPageDto) throws ParamInvalidException {
        staticPageDto.setId(id);
        staticPageFacade.update(staticPageDto);
        return ResponseHandler.message("Update thành công");
    }


    @GetMapping("/{id}")
    public ResponseObject<StaticPageDto> findById(@PathVariable("id") Long id) throws ParamInvalidException {
        StaticPageDto staticPageDto = staticPageFacade.getById(id);
        return ResponseHandler.response(staticPageDto);
    }

    @GetMapping("/path")
    public ResponseObject<StaticPageDto> findByPath(@RequestParam(value = "p") String path) throws ParamInvalidException {
        StaticPageDto staticPageDto = staticPageFacade.getByPath(path);
        return ResponseHandler.response(staticPageDto);
    }


    @DeleteMapping("/{id}")
    public ResponseObject<String> deleteById(@PathVariable("id") Long id) throws ParamInvalidException {
        staticPageFacade.deleteById(id);
        return ResponseHandler.response("Static delete successfully");
    }

}
