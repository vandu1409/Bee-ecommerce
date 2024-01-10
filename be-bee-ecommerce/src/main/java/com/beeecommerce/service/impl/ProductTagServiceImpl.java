package com.beeecommerce.service.impl;

import com.beeecommerce.constance.TypeMethod;
import com.beeecommerce.entity.Hashtag;
import com.beeecommerce.entity.Product;
import com.beeecommerce.entity.ProductTag;
import com.beeecommerce.mapper.ProductTagMapper;
import com.beeecommerce.model.dto.ProductTagDto;
import com.beeecommerce.repository.HashtagRepository;
import com.beeecommerce.repository.ProductRepository;
import com.beeecommerce.repository.ProductTagRepository;
import com.beeecommerce.service.ProductTagService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTagServiceImpl implements ProductTagService {

    private final ProductTagRepository productTagRepository;
    private final ProductTagMapper productTagMapper;
    private final HashtagRepository hashtagRepository;
    private final ProductRepository productRepository;

    @Override
    public Page<ProductTagDto> getAllProductTag(Pageable pageable) {
        return productTagRepository.findAll(pageable).map(productTagMapper);
    }


    @Override
    public ProductTagDto getProductTagById(Long id) {
        return productTagRepository
                .findById(id)
                .map(productTagMapper)
                .orElseThrow();
    }


//    @Override
//    public void createProductTag(ProductTagDto productTagDto) {
//        ProductTag productTag = new ProductTag();
//
//        Product product = productRepository.findById(productTagDto.getProductId()).orElse(null);
//        product.setId(product.getId());
//
//        Hashtag hashtag = hashtagRepository.findById(productTagDto.getHashtagDto().getId()).orElse(null);
//        hashtag.setId(hashtag.getId());
//
//        productTag.setProduct(product);
//        productTag.setTag(hashtag);
//
//        productTagRepository.save(productTag);
//
//    }


    @Override
    public void deleteProductTag(Long id) {
        productTagRepository.deleteById(id);
    }


//    @Override
//    public void update(Long id, ProductTagDto productTagDto) {
//        Optional<ProductTag> productTagOptional = productTagRepository.findById(id);
//
//        if (productTagOptional.isPresent()) {
//            ProductTag productTag = productTagOptional.get();
//
//            Product product = productRepository.findById(productTagDto.getProductId()).orElse(null);
//
//            Hashtag hashtag = hashtagRepository.findById(productTagDto.getHashtagDto().getId()).orElse(null);
//
//            productTag.setProduct(product);
//            productTag.setTag(hashtag);
//
//            productTagRepository.save(productTag);
//        }
//    }

    public ProductTag addToProductTag(String tag, Product product) {

        Hashtag hashtag = convertHashTag(tag);

        ProductTag productTag = ProductTag.builder()
                .tag(hashtag)
                .product(product)
                .build();

        return productTagRepository.save(productTag);


    }

    public ProductTag updateToProductTag(String tag, Product product) {

        ProductTag productTag = productTagRepository
                .findByProductAndTag(product.getId(), tag).orElse(null);

        if (productTag == null) {
            return addToProductTag(tag, product); //
        }

        return null;
    }

    public Hashtag convertHashTag(String tag) {
        Hashtag hashtag = hashtagRepository.findByName(tag).orElse(null);

        if (hashtag == null) {
            return hashtagRepository.save(Hashtag.builder().name(tag).build());
        }

        return hashtag;
    }


    public void removeStaleDeletedDataFromDB(List<String> tags, Product product) {
        List<ProductTag> productTags = productTagRepository.findByProduct(product.getId());

        List<Long> deletedProductTagIds = new ArrayList<>();

        for (ProductTag productTag : productTags) {
            if (!tags.contains(productTag.getTag().getName())) {
                deletedProductTagIds.add(productTag.getId());
            }
        }

        productTagRepository.deleteAllById(deletedProductTagIds);
    }


    @Override
    public void saveAndUpdateListTag(List<String> tags, Product product, TypeMethod type) {
        for (String tag : tags) {
            if (!ObjectHelper.checkNull(tag)) {
                if (type.equals(TypeMethod.CREATE)) {
                    addToProductTag(tag, product);
                } else if (type.equals(TypeMethod.UPDATE)) {
                    updateToProductTag(tag, product);
                }

            }
        }

        if (type.equals(TypeMethod.UPDATE)) {
            removeStaleDeletedDataFromDB(tags, product);
        }

    }


}