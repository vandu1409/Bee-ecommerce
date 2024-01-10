package com.beeecommerce.controller;

import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.facade.StatisticalFacade;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.beeecommerce.constance.ApiPaths.StatisticalPath.STATISTICAL_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping(STATISTICAL_PREFIX)
public class StatisticalController {

    private final StatisticalFacade statisticalFacade;

    @GetMapping()
    public ResponseObject<?> statistical() throws AuthenticateException {
        return ResponseHandler.response(statisticalFacade.statistical());
    }
}
