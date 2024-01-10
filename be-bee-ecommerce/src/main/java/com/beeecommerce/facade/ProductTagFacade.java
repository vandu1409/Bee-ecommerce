package com.beeecommerce.facade;

import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.ProductTagDto;
import com.beeecommerce.service.ProductTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductTagFacade {
    private final ProductTagService productTagService;

    public Page<ProductTagDto> getAll(Pageable pageable) {

        return productTagService.getAllProductTag(pageable);
    }

//    public void create(ProductTagDto productTagDto) throws ParamInvalidException {
//        CheckNullUtil.checkNullParam(productTagDto.getProductId(), productTagDto.getHashtagDto());
//
//        productTagService.createProductTag(productTagDto);
//    }

    public void deleteProductTagById(Long id) throws ParamInvalidException {
        if (id == null) {
            throw new ParamInvalidException("Không tìm thấy id");
        }
        try {
            productTagService.deleteProductTag(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi xóa ", e);
        }
    }

    public ProductTagDto findById(Long id) throws ParamInvalidException {
        if (id == null) {
            throw new ParamInvalidException("Không tìm thấy id");
        } else {
            return productTagService.getProductTagById(id);
        }
    }

//    public void updateProductTag(Long id, ProductTagDto productTagDto) throws ParamInvalidException {
//        CheckNullUtil.checkNullParam(id);
//        CheckNullUtil.checkNullParam(productTagDto.getProductId(), productTagDto.getHashtagDto());
//        try {
//            productTagDto.setId(id);
//            productTagService.update(id, productTagDto);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi cập nhật ", e);
//        }
//    }
}