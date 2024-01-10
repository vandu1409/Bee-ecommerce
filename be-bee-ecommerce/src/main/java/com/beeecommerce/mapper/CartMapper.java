package com.beeecommerce.mapper;

import com.beeecommerce.entity.Cart;
import com.beeecommerce.model.dto.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CartMapper implements Function<Cart, CartDto> {

    private final ClassifyMapper classifyMapper;

    private final SimpleProductResponseMapper productMapper;

    @Override
    public CartDto apply(Cart cart) {
        return CartDto
                .builder()
                .id(cart.getId())
                .quantity(cart.getQuantity())
                .classifyId(cart.getClassify().getId())
                .classify(classifyMapper.apply(cart.getClassify()))
                .product(productMapper.apply(cart.getProduct()))
                .vendorName(cart.getProduct().getVendors().getName())
                .vendorId(cart.getProduct().getVendors().getId())
                .build();
    }
}
