package com.beeecommerce.controller;

import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.facade.WishListFacade;
import com.beeecommerce.model.dto.WishlistDto;
import com.beeecommerce.model.response.SimpleProductResponse;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.WishlistPath.WISHLIST_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping(WISHLIST_PREFIX)
public class WishlistController {

    private final WishListFacade wishListFacade;

    @GetMapping
    public ResponseObject<List<SimpleProductResponse>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SimpleProductResponse> voucherDtoPage = wishListFacade.findByCurrentUser(pageable);

        return ResponseHandler
                .response(voucherDtoPage)
                .message("Lấy Thành Công");
    }
//    @GetMapping
//    public ResponseObject<List<WishlistDto>> getAll(
//            @RequestParam(defaultValue = "0") Integer page,
//            @RequestParam(defaultValue = "10") Integer size
//    ) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<WishlistDto> voucherDtoPage = wishListFacade.getAll(pageable);
//
//        return ResponseHandler
//                .response(voucherDtoPage)
//                .message("Lấy Thành Công");
//    }

    @PostMapping
    public ResponseObject<?> saveOrUpdateVoucher(@RequestBody WishlistDto wishlistDto) throws ParamInvalidException {
        boolean result = wishListFacade.create(wishlistDto);
        return ResponseHandler
                .message(result ? "Liked" : "Dislike")
                .result(result);
    }

    @DeleteMapping("/{id}")
    public ResponseObject<?> deleteVoucherById(@PathVariable("id") Long id)
            throws EntityNotFoundException, ParamInvalidException {
        wishListFacade.deleteWishListById(id);
        return ResponseHandler.message("Xóa Thành Công");
    }

    @GetMapping("find-current-user")
    public ResponseObject<?> findByCurrentUser( @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){

        Pageable pageable = PageRequest.of(page, size);
        return ResponseHandler.response(wishListFacade.findByCurrentUser(pageable));
    }


}
