package com.beeecommerce.facade;

import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.WishlistDto;
import com.beeecommerce.model.response.SimpleProductResponse;
import com.beeecommerce.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListFacade {

    private final WishlistService wishlistService;

//    public Page<WishlistDto> getAll(Pageable pageable) {
//        return wishlistService.getAll(pageable);
//    }

    public boolean create(WishlistDto wishlistDto) throws ParamInvalidException {
        if (wishlistDto == null) {
            throw new ParamInvalidException("Không Có Dử Liệu");
        }
        return wishlistService.create(wishlistDto);
    }

    private void checkIdNull(Long id) throws ParamInvalidException {
        if (id == null) {
            throw new ParamInvalidException("Brand not found");
        }
    }

    public void deleteWishListById(Long id) throws EntityNotFoundException, ParamInvalidException {
        checkIdNull(id);
        wishlistService.deleteWishList(id);

    }

    public Page<SimpleProductResponse> findByCurrentUser(Pageable pageable){
        return wishlistService.findByCurrentUser(pageable);
    }
}
