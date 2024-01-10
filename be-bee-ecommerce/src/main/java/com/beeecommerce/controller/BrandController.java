package com.beeecommerce.controller;

import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.facade.BrandFacade;
import com.beeecommerce.model.dto.AttributeDto;
import com.beeecommerce.model.dto.BrandDto;
import com.beeecommerce.model.dto.CartDto;
import com.beeecommerce.model.dto.CategoryDetailDto;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.BrandPath.BRAND_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping(BRAND_PREFIX)
public class BrandController {
    private final BrandFacade brandFacade;


@GetMapping("")
    public ResponseObject<List<BrandDto>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<BrandDto> categories = brandFacade.getAll(pageable);

        return ResponseHandler.response(categories);
    }

    @PostMapping()
    public ResponseObject<String> insert(@RequestParam String name,
                                         @RequestPart(name = "image", required = false) MultipartFile image)
            throws ApplicationException {

        BrandDto brandDto = new BrandDto();
        brandDto.setName(name);

        brandFacade.insert(brandDto, image);

        return ResponseHandler.response("Thêm mới thành công");
    }

    @PutMapping("{id}")
    public ResponseObject<String> update(@PathVariable Long id,
                                           @RequestParam String name,
                                           @RequestPart(name = "image", required = false) MultipartFile image)
            throws ApplicationException {
        BrandDto brandDto = new BrandDto();
        brandDto.setName(name);
        brandFacade.update(id, brandDto, image);

        return ResponseHandler.response("Update thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseObject<String> delete(@PathVariable Long id) throws ApplicationException {
        brandFacade.delete(id);
        return ResponseHandler.response("Xóa thành công");
    }

    @GetMapping("/{id}")
            public ResponseObject<BrandDto> findById(@PathVariable("id") Long id) throws ParamInvalidException {
        BrandDto attributeDtoOptional = brandFacade.findById(id);
        return ResponseHandler.response(attributeDtoOptional);
    }
}
