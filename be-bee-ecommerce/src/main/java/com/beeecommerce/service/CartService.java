package com.beeecommerce.service;

import com.beeecommerce.entity.Cart;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.CartDto;
import com.beeecommerce.model.dto.VendorDto;
import com.beeecommerce.model.request.AddCartRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {


    Page<CartDto> getAll(Pageable pageable);

    Cart addToCart(AddCartRequest addCartRequest) throws AuthenticateException;

    CartDto updateCart(Long id, Long quantity) throws ParamInvalidException;

    void removeCart(Long id);

    List<Cart> getCartByIds(List<Long> ids);

    Long getLoggedInUserCartCount();
  
    Page<CartDto> getUserCarts(Pageable pageable);

    VendorDto checkSameVendor(List<Long> ids);
}
