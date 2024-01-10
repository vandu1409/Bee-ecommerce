package com.beeecommerce.facade;


import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.service.OrderService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import com.beeecommerce.model.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;

    public void changeStatus(Long orderId) throws ApplicationException {
        ObjectHelper.checkNullParam(orderId);

        orderService.changeStatus(orderId);
    }

    public void cancelClient(Long orderId) throws ApplicationException {
        ObjectHelper.checkNullParam(orderId);

        orderService.cancelClient(orderId);
    }

    public void cancelAdmin(Long orderId) throws ApplicationException {
        ObjectHelper.checkNullParam(orderId);

        orderService.cancelAdmin(orderId);
    }

    public Page<OrderDto> findByUserLogged(Pageable pageable) {
        return orderService.findByUserLogged(pageable);
    }

    public Page<OrderDto> findByVendorsLogged(Pageable pageable) {
        return orderService.findByVendorLogged(pageable);
    }

    public OrderDto findById(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);
        return orderService.findById(id);
    }

    public void confirmed(Long orderId) throws ParamInvalidException {
        ObjectHelper.checkNullParam(orderId);
        orderService.confirmed(orderId);
    }
}
