package com.beeecommerce.service;

import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.VoucherNotApplicableException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.OrderDto;
import com.beeecommerce.model.request.CheckoutRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDto> getAll(Pageable pageable);

    OrderDto insert(CheckoutRequest checkoutRequest) throws EntityNotFoundException, ApplicationException, VoucherNotApplicableException;

    @Transactional
    void changeStatus(Long orderId) throws ApplicationException;

    void cancelClient(Long orderId) throws ApplicationException;

    void cancelAdmin(Long orderId) throws ApplicationException;
  
    OrderDto findById(Long id);

    Page<OrderDto> findByUserLogged(Pageable pageable);

    Page<OrderDto> findByVendorLogged(Pageable pageable);

    String paymentVnpay(Long orderId);

    void confirmed(Long orderId);
}
