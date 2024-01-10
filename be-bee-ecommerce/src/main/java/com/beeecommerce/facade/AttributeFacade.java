package com.beeecommerce.facade;

import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.AttributeDto;
import com.beeecommerce.service.AttributeService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AttributeFacade {

    private final AttributeService attributeService;

    public Page<AttributeDto> getAll(Pageable pageable) {
        return attributeService.getAll(pageable);
    }

    public void createAttribute(AttributeDto attributeDto) throws ParamInvalidException {
        ObjectHelper.checkNullParam(attributeDto.getName());
        attributeService.createAttribute(attributeDto);

    }

    public AttributeDto findById(Long id) throws ParamInvalidException {

        ObjectHelper.checkNullParam(id);

        return attributeService.findById(id);


    }

    public void updateAttribute(Long id, String name) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id, name);


        try {
            attributeService.updateAttribute(id, name);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi cập nhật ", e);
        }
    }


    public void deleteAttribute(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);
        try {
            attributeService.deleteAttribute(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi xóa ", e);
        }
    }

    public List<AttributeDto> getRequireAttribute(Long categoryId) throws EntityNotFoundException {
        ObjectHelper.checkNull(categoryId);

        return attributeService.getRequireAttribute(categoryId);


    }


    public List<AttributeDto> searchAttribute(String key) {
        return attributeService.searchAttribute(key);
    }
}
