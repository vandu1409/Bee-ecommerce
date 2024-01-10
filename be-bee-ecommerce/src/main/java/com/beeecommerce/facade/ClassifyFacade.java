package com.beeecommerce.facade;

import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.response.ClassifyResponse;
import com.beeecommerce.service.ClassifyService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassifyFacade {

    private final ClassifyService classifyService;

    public ClassifyResponse getClassify(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);

        return classifyService.getClassify(id);
    }
}
