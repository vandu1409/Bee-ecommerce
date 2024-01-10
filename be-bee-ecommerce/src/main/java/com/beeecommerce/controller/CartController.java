package com.beeecommerce.controller;

import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.facade.CartFacade;
import com.beeecommerce.model.dto.CartDto;
import com.beeecommerce.model.dto.VendorDto;
import com.beeecommerce.model.request.AddCartRequest;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.CartPath.CART_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping(CART_PREFIX)
public class CartController {
    private final CartFacade cartFacade;

//    @GetMapping
//    public ResponseObject<List<CartDto>> getAll(
//            @RequestParam(defaultValue = "0") Integer page,
//            @RequestParam(defaultValue = "10") Integer size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<CartDto> listCartDto = cartFacade.getAll(pageable);
//        return ResponseHandler.response(listCartDto);
//    }

    @GetMapping()
    public ResponseObject<List<CartDto>> geUserCarts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size
    ) throws ParamInvalidException {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<CartDto> listCartDto = cartFacade.getUserCarts(pageable);
        return ResponseHandler.response(listCartDto);
    }


    @DeleteMapping("{id}")
    public ResponseObject<String> removeCart(@PathVariable("id") Long id) throws ParamInvalidException {
        cartFacade.deleteCartById(id);
        return ResponseHandler.response("Xóa Thành Công");
    }

    @PutMapping()
    public ResponseObject<?> update(@RequestBody CartDto cartDto) throws ParamInvalidException {
        cartFacade.updateCart(cartDto);
        return ResponseHandler.message("Cập Nhật Thành Công");
    }

    @PostMapping
    public ResponseObject<String> addToCart(@RequestBody AddCartRequest addCartRequest) throws ParamInvalidException, AuthenticateException {
        cartFacade.addToCart(addCartRequest);
        return ResponseHandler.response("Lưu Thành Công");
    }

    @GetMapping("count")
    public ResponseObject<?> getLoggedInUserCartCount(){
        return ResponseHandler.response(cartFacade.getLoggedInUserCartCount());
    }

    @GetMapping("check")
    public ResponseObject<VendorDto> check(@RequestParam List<Long> ids) throws ParamInvalidException {
        return ResponseHandler.response(cartFacade.checkSameVendor(ids));
    }

}
