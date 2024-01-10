package com.beeecommerce.controller;

import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.facade.ClassifyFacade;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class ClassifyController {

    private final ClassifyFacade classifyFacade;

    @GetMapping
    public ResponseObject<?> getClassify(@RequestParam Long id) throws ParamInvalidException {

        return ResponseHandler.response(classifyFacade.getClassify(id));
    }
}
