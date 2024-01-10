package com.beeecommerce.controller;

import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.facade.OrderFacade;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import com.beeecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.OrderDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderFacade orderFacade;

    @PutMapping("change-status/{id}")
    public ResponseObject<?> changeStatus(@PathVariable("id") Long id) throws ApplicationException {

        orderFacade.changeStatus(id);

        return ResponseHandler.response("Thay đổi trạng thái thành công!");
    }

    @DeleteMapping("/admin/cancel/{id}")
    public ResponseObject<?> cancelAdmin(@PathVariable("id") Long id) throws ApplicationException {
        orderFacade.cancelAdmin(id);
        return ResponseHandler.response("Hủy đơn hàng thành công!");
    }

    @DeleteMapping("/client/cancel/{id}")
    public ResponseObject<?> cancelClient(@PathVariable("id") Long id) throws ApplicationException {
        orderFacade.cancelClient(id);
        return ResponseHandler.response("Hủy đơn hàng thành công!");
    }

    @PutMapping("/client/confirmed/{id}")
    public ResponseObject<?> confirmedClient(@PathVariable("id") Long id) throws ApplicationException {
        orderFacade.confirmed(id);
        return ResponseHandler.response("Xác nhận đơn hàng thành công!");
    }

    @GetMapping("find-user-logged")
    public ResponseObject<List<OrderDto>> findByUserLogged(@RequestParam(defaultValue = "0") Integer page,
                                                           @RequestParam(defaultValue = "999") Integer size) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);

        return ResponseHandler.response(orderFacade.findByUserLogged(pageable));
    }

    @GetMapping("find-vendor-logged")
    public ResponseObject<List<OrderDto>> findByVendorLogged(@RequestParam(defaultValue = "0") Integer page,
                                                             @RequestParam(defaultValue = "10") Integer size) {


        Pageable pageable = Pageable.ofSize(size).withPage(page);

        return ResponseHandler.response(orderFacade.findByVendorsLogged(pageable));
    }

    @GetMapping("find/{id}")
    public ResponseObject<?> findById(@PathVariable("id")Long id) throws ParamInvalidException {
        return ResponseHandler.response(orderFacade.findById(id));
    }

}
