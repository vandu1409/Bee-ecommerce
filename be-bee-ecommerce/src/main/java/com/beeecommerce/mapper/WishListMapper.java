package com.beeecommerce.mapper;

import com.beeecommerce.entity.Product;
import com.beeecommerce.entity.Wishlist;
import com.beeecommerce.model.dto.WishlistDto;
import com.beeecommerce.model.response.SimpleProductResponse;
import com.beeecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class WishListMapper implements Function<Wishlist, SimpleProductResponse> {

    private final ProductRepository productRepository;

    private final SimpleProductResponseMapper simpleProductResponseMapper;
    @Override
    public SimpleProductResponse apply(Wishlist wishlist) {

        Product product = wishlist.getProduct();

        return simpleProductResponseMapper.apply(product);
    }
}
