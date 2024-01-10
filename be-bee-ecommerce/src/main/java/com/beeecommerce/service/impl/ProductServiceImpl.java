package com.beeecommerce.service.impl;

import com.beeecommerce.constance.TypeMethod;
import com.beeecommerce.entity.*;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.facade.ProductImageFacade;
import com.beeecommerce.mapper.ProductDetailResponseMapper;
import com.beeecommerce.mapper.ProductMapper;
import com.beeecommerce.mapper.SimpleProductResponseMapper;
import com.beeecommerce.mapper.VendorMapper;
import com.beeecommerce.model.dto.AttributeDto;
import com.beeecommerce.model.dto.ClassifyDto;
import com.beeecommerce.model.dto.ProductDto;
import com.beeecommerce.model.dto.VendorDto;
import com.beeecommerce.model.request.ClassifyRequest;
import com.beeecommerce.model.request.ProductUpdateRequest;
import com.beeecommerce.model.request.UpdateProductRequest;
import com.beeecommerce.model.response.ProductDetailResponse;
import com.beeecommerce.model.response.SimpleProductResponse;
import com.beeecommerce.repository.*;
import com.beeecommerce.service.*;
import com.beeecommerce.util.ObjectHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ClassifyService classifyService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AccountRepository accountRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageFacade productImageFacade;
    private final ClasstifyGroupRepository classtifyGroupRepository;
    private final ClassifyRepository classifyRepository;

    private final ClassifyNameRepository classifyNameRepository;
    private final ProductTagService productTagService;
    private final ProductAttributesService productAttributesService;
    private final ProductDetailResponseMapper productDetailResponseMapper;
    private final SimpleProductResponseMapper simpleProductResponseMapper;

    private final ProductTagRepository productTagRepository;
    private final AccountService accountService;

    private final ProductAttributeRepository productAttributeRepository;
    private final VendorMapper vendorMapper;

    @Override
    public Page<SimpleProductResponse> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable).map(simpleProductResponseMapper);
    }


    @Override
    public ProductDetailResponse getProductById(Long id) {
        return productRepository
                .findById(id)
                .map(productDetailResponseMapper)
                .orElseThrow();
    }

    @Override
    public ProductDto edit(Long id) {
        return productRepository
                .findById(id)
                .map(productMapper)
                .orElseThrow();
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Không tìm thấy sản phẩm!")
                );

        product.setIsDelete(true);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void createProduct(ProductDto productDto) throws ApplicationException {
        Product product = productMapper.reverse(productDto); // Chuển qua mapper

        product.setVendors(accountService.getAuthenticatedAccount().getVendor());
        product.setCreateAt(LocalDate.now());
        product.setUpdateAt(LocalDate.now());
        product.setViewCount(0L);

        Product dbProduct = productRepository.save(product);

        classifyService.saveOrUpdate(
                productDto.getClassifies(),
                productDto.getClassifyGroupName(),
                productDto.getClassifyName(),
                dbProduct
        );

        if (productDto.getFiles() != null) {
            for (MultipartFile file : productDto.getFiles()) {
                productImageFacade.addToProductImage(file, dbProduct);
            }
        }

        productAttributesService.saveAndUpdateListAttributes(productDto.getAttributes(), dbProduct, TypeMethod.CREATE);
        productTagService.saveAndUpdateListTag(productDto.getTags(), dbProduct, TypeMethod.CREATE);

    }

    @Override
    public void update(Long id, ProductDto productDto) throws ApplicationException {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            setProductDetails(existingProduct, productDto);

            Product dbProduct = productRepository.save(existingProduct);

            classifyService.saveOrUpdate(productDto.getClassifies(),
                    productDto.getClassifyGroupName(),
                    productDto.getClassifyName(),
                    dbProduct);

            if (productDto.getFiles() != null) {
                for (MultipartFile file : productDto.getFiles()) {
                    productImageFacade.addToProductImage(file, dbProduct);
                }
            }

            removeStaleDeletedDataFromDB(productDto);
            productAttributesService.saveAndUpdateListAttributes(productDto.getAttributes(), dbProduct, TypeMethod.UPDATE);
            productTagService.saveAndUpdateListTag(productDto.getTags(), dbProduct, TypeMethod.UPDATE);

        }

    }


    private void setProductDetails(Product product, ProductDto productDto) throws EntityNotFoundException, AuthenticateException {

        BeanUtils.copyProperties(productDto, product);

        product.setVendors(accountService.getAuthenticatedAccount().getVendor());
        product.setBrand(brandRepository.findById(productDto.getBrandId()).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy thương hiệu sản phẩm!")));
        product.setCategory(categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
                () -> new EntityNotFoundException("Không tìm danh mục sản phẩm!")));


    }


    public void removeStaleDeletedDataFromDB(ProductDto productDto) throws ApplicationException {

        Product product = productRepository.findById(productDto.getId()).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy sản phẩm!"));

        ProductDto currentProduct = productMapper.apply(product);

        List<ClassifyDto> result = new ArrayList<>(currentProduct.getClassifies());
        result.removeAll(product.getClassifies());

        List<Long> classifyId = result.stream().map(ClassifyDto::getClassifyId).toList();

        classifyRepository.deleteAllById(classifyId);

        List<String> resultTags = new ArrayList<>(currentProduct.getTags());
        resultTags.removeAll(productDto.getTags());

        List<ProductTag> hashtagList = resultTags
                .stream().map(item ->
                        productTagRepository.findByProductAndTag(currentProduct.getId(), item)
                                .orElse(null)).toList();

        productTagRepository.deleteAll(hashtagList);

        List<AttributeDto> resultAttributes = new ArrayList<>(currentProduct.getAttributes());
        resultAttributes.removeAll(productDto.getAttributes());

        List<ProductAttribute> productAttributeList = resultAttributes
                .stream().map(item ->
                        productAttributeRepository
                                .findByProductAndAttributes(currentProduct.getId(), item.getName()).orElse(null)).toList();

        productAttributeRepository.deleteAll(productAttributeList);

    }

    @Override
    public void updateProductHidden(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy sản phẩm !"));

        product.setIsHidden(!product.getIsHidden());

        productRepository.save(product);
    }


    @Override
    public void updateClassifyPriceAndInventory(Long productId, ProductUpdateRequest request) throws ApplicationException {

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy sản phẩm !"));

        for (ClassifyRequest classifyRequest : request.getClassifyRequests()
        ) {
            Classify classify = classifyRepository.findById(classifyRequest.getClassifyId()).orElseThrow(
                    () -> new EntityNotFoundException("Không tìm thấy sản phẩm !"));
            if (classify.getGroup().getProduct().getId() != product.getId()) {
                throw new ApplicationException("Không tim thấy sản phẩm!");
            }

            if (!ObjectHelper.checkNull(classifyRequest.getInventory())) {
                Long difference = classifyRequest.getInventory() - classify.getQuantity();

                classify.setQuantity(classify.getQuantity() + difference);
                classify.setInventory(classifyRequest.getInventory());
            }

            if (!ObjectHelper.checkNull(classifyRequest.getPrice())) {
                classify.setSellPrice(classifyRequest.getPrice());
            }

            classifyRepository.save(classify);
        }

    }


    @Override
    public Page<SimpleProductResponse> findAllByVendor(Long userId, Pageable pageable) {
        return findAllByVendor(pageable, userId).map(simpleProductResponseMapper);
    }

    private Page<Product> findAllByVendor(Pageable pageable, Long userId) {
//        Account account = accountService.findById(userId).orElseThrow(
//                () -> new EntityNotFoundException("Không tìm thấy tài khoản !"));

        return productRepository
                .findAllByUser(userId, pageable)
                ;

    }

    @Override
    public Page<SimpleProductResponse> findAllByVendor(Pageable pageable) throws AuthenticateException {
        Account account = accountService.getAuthenticatedAccount();
        return findAllByVendor(account.getId(), pageable);
    }

    @Override
    public Page<ProductDetailResponse> getAllProductDetail(Pageable pageable) {
        Account account = accountService.getAuthenticatedAccount();
        return findAllByVendor(pageable, account.getVendor().getId()).map(productDetailResponseMapper);
    }

    @Override
    public VendorDto findByProduct(Long id) {
        Vendor vendor = productRepository.findByProduct(id).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy thông tin shop!"));

        return vendorMapper.apply(vendor);

    }

    @Override
    public void updateClassifyName(UpdateProductRequest request) {

        productRepository
                .findById(request.getProductId())
                .ifPresent(product -> {
                    ClassifyName groupName = product.getClassifyGroups().get(0).getClassifyName();
                    ClassifyName classifyName = product.getClassifies().get(0).getClassifyName();

                    classifyName.setName(request.getClassifyName());
                    groupName.setName(request.getClassifyGroupName());

                    classifyNameRepository.saveAll(List.of(classifyName, groupName));
                });
        ;
    }

    @Override
    public void updateProduct(UpdateProductRequest updateProductRequest) {
        Product product = productRepository.findById(updateProductRequest.getProductId()).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy sản phẩm!"));

        BeanUtils.copyProperties(updateProductRequest, product);

        productTagRepository.deleteAll(product.getProductTags());


        productAttributesService.saveAndUpdateListAttributes(updateProductRequest.getAttributes(),
                product, TypeMethod.UPDATE);


        productTagService.saveAndUpdateListTag(updateProductRequest.getTags(), product, TypeMethod.UPDATE);


    }

    public void updateClassify(List<ClassifyDto> list, Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy sản phẩm!"));


    }
    @Override
    public List<SimpleProductResponse> findByCategoryId(long parentId) {
        List<SimpleProductResponse> result = productRepository.findByCategoryId(parentId)
                .stream()
                .map(simpleProductResponseMapper)
                .toList();

        return result;
    }

    @Override
    public List<SimpleProductResponse> findByCategoryParentId(long parentId) {
        List<SimpleProductResponse> result = productRepository.findByCategoryParentId(parentId)
                .stream()
                .map(simpleProductResponseMapper)
                .toList();

        return result;
    }


}
