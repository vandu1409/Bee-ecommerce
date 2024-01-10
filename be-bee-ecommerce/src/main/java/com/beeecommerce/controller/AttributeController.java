package com.beeecommerce.controller;

import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.facade.AttributeFacade;
import com.beeecommerce.model.dto.AttributeDto;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.AttributePath.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ATTRIBUTE_PREFIX)
public class AttributeController {

    private final AttributeFacade attributeFacade;

    @GetMapping
    public ResponseObject<List<AttributeDto>> gets(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<AttributeDto> attributes = attributeFacade.getAll(pageable);

        return ResponseHandler.response(attributes);
    }

    @GetMapping("/{id}")
    public ResponseObject<AttributeDto> findByIdAttribute(@PathVariable("id") Long id) throws ParamInvalidException {
        AttributeDto attributeDtoOptional = attributeFacade.findById(id);
        return ResponseHandler.response(attributeDtoOptional);
    }


    @PostMapping
    public ResponseObject<String> createAttribute(@RequestBody AttributeDto attributeDto) throws ParamInvalidException {
        attributeFacade.createAttribute(attributeDto);
        return ResponseHandler.response("Thành Công");
    }

    @PutMapping("/{id}")
    public ResponseObject<String> updateAttribute(
            @PathVariable("id") Long idAttribute,
            @RequestParam("name") String name
    ) throws ParamInvalidException {
        attributeFacade.updateAttribute(idAttribute, name);

        return ResponseHandler.response("Cập Nhật Thành Công");
    }

    @DeleteMapping("/{id}")
    public ResponseObject<String> deleteAttribute(@PathVariable("id") Long idAttribute) throws ParamInvalidException {
        attributeFacade.deleteAttribute(idAttribute);
        return ResponseHandler.response("Xóa Thành Công");
    }

    @GetMapping(ATTRIBUTE_REQUIRE)
    public ResponseObject<List<AttributeDto>> findAllByCategory(@PathVariable Long categoryId) throws EntityNotFoundException {
        return ResponseHandler.response(
                attributeFacade.getRequireAttribute(categoryId)
        );
    }

    @GetMapping(SEARCH_ATTRIBUTE)
    public ResponseObject<List<AttributeDto>> searchAttribute(@RequestParam String key) throws ParamInvalidException {
        return ResponseHandler.response(
                attributeFacade.searchAttribute(key)
        );
    }

}

