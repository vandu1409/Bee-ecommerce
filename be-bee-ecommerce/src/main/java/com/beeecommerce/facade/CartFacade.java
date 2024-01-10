package com.beeecommerce.facade;

import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.CartDto;
import com.beeecommerce.model.dto.VendorDto;
import com.beeecommerce.model.request.AddCartRequest;
import com.beeecommerce.service.CartService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartFacade {

    private final CartService cartService;

    public Page<CartDto> getAll(Pageable pageable) {
        return cartService.getAll(pageable);
    }

    public void deleteCartById(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);

        cartService.removeCart(id);

    }

    public void addToCart(AddCartRequest addCartRequest) throws ParamInvalidException, AuthenticateException {
        ObjectHelper.checkNullParam(addCartRequest.getClasstyId(), addCartRequest.getQuantity());
        cartService.addToCart(addCartRequest);

    }

    public CartDto updateCart(CartDto cartDto) throws ParamInvalidException {

        ObjectHelper.checkNullParam(cartDto.getId(),cartDto.getQuantity());

        return cartService.updateCart(cartDto.getId(),cartDto.getQuantity());
    }

    public Long getLoggedInUserCartCount(){
        return cartService.getLoggedInUserCartCount();
    }


    public Page<CartDto> getUserCarts(Pageable pageable) {
        return cartService.getUserCarts(pageable);
    }

    public VendorDto checkSameVendor(List<Long> ids) throws ParamInvalidException {
        if (!ids.isEmpty()) {
           return cartService.checkSameVendor(ids);
        }
        throw new ParamInvalidException("Chưa chọn sản phẩm");
    }
}
