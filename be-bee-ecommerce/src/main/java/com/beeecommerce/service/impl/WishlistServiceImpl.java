package com.beeecommerce.service.impl;

import com.beeecommerce.entity.Account;
import com.beeecommerce.entity.Product;
import com.beeecommerce.entity.Wishlist;
import com.beeecommerce.mapper.WishListMapper;
import com.beeecommerce.model.dto.WishlistDto;
import com.beeecommerce.model.response.SimpleProductResponse;
import com.beeecommerce.repository.AccountRepository;
import com.beeecommerce.repository.ProductRepository;
import com.beeecommerce.repository.WishlistRepository;
import com.beeecommerce.service.AccountService;
import com.beeecommerce.service.WishlistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WishListMapper wishListMapper;

    @Autowired
    private AccountService accountService;


//    @Override
//    public Page<Sim> getAll(Pageable pageable) {
//        return wishlistRepository.findAll(pageable).map(wishListMapper);
//    }

    @Override
    public boolean create(WishlistDto wishlistDto) {

        Account account = accountService.getAuthenticatedAccount();

        // tồn tại thì xóa
        Wishlist existingWishlistItem = wishlistRepository.findByUserIdAndProductId(account.getId(),
                wishlistDto.getProductId());
        if (existingWishlistItem != null) {

            wishlistRepository.deleteById(existingWishlistItem.getId());
            return false;
        }

        Wishlist wishlistItem = new Wishlist();

        Product product = new Product();
        product.setId(wishlistDto.getProductId());

        wishlistItem.setUser(account);
        wishlistItem.setProduct(product);
        
        wishlistRepository.save(wishlistItem);
        return true;

    }

    @Override
    public void deleteWishList(Long id) {
        wishlistRepository.deleteById(id);
    }


    public Page<SimpleProductResponse> findByUser(Long id,Pageable pageable){
        return wishlistRepository.findByUser(id,pageable).map(wishListMapper);
    }

    @Override
    public Page<SimpleProductResponse> findByCurrentUser(Pageable pageable){
        Account account = accountService.getAuthenticatedAccount();

       return findByUser(account.getId(),pageable);
    }

}
