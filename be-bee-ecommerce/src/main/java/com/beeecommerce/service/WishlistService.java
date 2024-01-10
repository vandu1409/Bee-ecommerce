package com.beeecommerce.service;

import com.beeecommerce.model.dto.WishlistDto;
import com.beeecommerce.model.response.SimpleProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishlistService {
//    Page<WishlistDto> getAll(Pageable pageable);

    boolean create(WishlistDto wishlistDto);

    void deleteWishList(Long id);

    Page<SimpleProductResponse> findByCurrentUser(Pageable pageable);
}
