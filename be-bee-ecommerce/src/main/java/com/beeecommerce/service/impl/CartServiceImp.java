package com.beeecommerce.service.impl;

import com.beeecommerce.entity.Account;
import com.beeecommerce.entity.Cart;
import com.beeecommerce.entity.Classify;
import com.beeecommerce.entity.Vendor;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.core.ApplicationRuntimeException;
import com.beeecommerce.mapper.CartMapper;
import com.beeecommerce.mapper.VendorMapper;
import com.beeecommerce.model.dto.CartDto;
import com.beeecommerce.model.dto.VendorDto;
import com.beeecommerce.model.request.AddCartRequest;
import com.beeecommerce.repository.CartRepository;
import com.beeecommerce.repository.ClassifyRepository;
import com.beeecommerce.repository.ProductRepository;
import com.beeecommerce.service.AccountService;
import com.beeecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {


    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final AccountService accountService;
    private final CartMapper cartMapper;
    private final ClassifyRepository classifyRepository;
    private final VendorMapper vendorMapper;

    @Override
    public Page<CartDto> getAll(Pageable pageable) {
        return cartRepository.findAll(pageable).map(cartMapper);
    }

    @Override
    public Cart addToCart(AddCartRequest addCartRequest) throws AuthenticateException {
        Optional<Classify> classifyId = classifyRepository.findById(addCartRequest.getClasstyId());
        Account acc = accountService.getAuthenticatedAccount();
        Cart existingCart = cartRepository.findByClassifyIdAndUserId(classifyId.get().getId(), acc.getId());

        if (existingCart != null) {

            existingCart.setQuantity(existingCart.getQuantity() + 1);

            return cartRepository.save(existingCart);
        } else {

            Classify classify = new Classify();
            classify.setId(addCartRequest.getClasstyId());

            Cart cart = new Cart();

            cart.setQuantity(addCartRequest.getQuantity());
            cart.setClassify(classify);
            cart.setUser(acc);
                return cartRepository.save(cart);
        }
    }

    @Override
    public CartDto updateCart(Long id, Long quantity) throws ParamInvalidException {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Không tìm thấy giỏ hàng có id: " + id)
                );

        Long inventory = cart.getClassify().getInventory();

        if (quantity < 0) {
            throw new ParamInvalidException("Số lượng sản phẩm phải lớn hơn 0!");
        }

        if (quantity <= inventory) {
            cart.setQuantity(quantity);
        } else {
            throw new ParamInvalidException("Số lượng hàng trong không không đủ!");
        }

        return cartMapper.apply(
                cartRepository.save(cart)
        );
    }


    @Override
    public void removeCart(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public List<Cart> getCartByIds(List<Long> ids) {
        return cartRepository.findAllById(ids);
    }

    @Override
    public Long getLoggedInUserCartCount(){
        Account account = accountService.getAuthenticatedAccount();


        Long count = cartRepository.countCartsByUserId(account.getId());

        return count;
    }

 
    @Override
    public Page<CartDto> getUserCarts(Pageable pageable) {
        Account acc = accountService.getAuthenticatedAccount();
        return cartRepository
                .getUserCarts(acc.getId(), pageable)
                .map(cartMapper);
    }

    @Override
    public VendorDto checkSameVendor(List<Long> ids) {
        List<Vendor> vendors =cartRepository
                .findAllById(ids)
                .stream()
                .map(cart -> cart.getProduct().getVendors())
                 .distinct()
                 .toList();

        if (vendors.size() > 1) {
            throw new ApplicationRuntimeException("Chúng tôi chưa hỗ trợ mua sản phẩm của nhiều nhà cung cấp khác nhau trong cùng 1 đơn hàng");
        }
        return vendorMapper.apply(vendors.get(0));
    }
}

