package com.beeecommerce.controller;

import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.facade.FeedbackFacade;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import jakarta.mail.Multipart;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.FeedbackPath.FEEDBACK_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping(FEEDBACK_PREFIX)
public class FeedbackController {

    private final FeedbackFacade feedbackFacade;

    @GetMapping()
    public ResponseObject<?> getFeedback(@RequestParam Long idProd,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) throws ParamInvalidException {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseHandler.response(
                feedbackFacade.getByProduct(idProd, pageable));
    }

    @PostMapping()
    public ResponseObject<?> createFeedback(@RequestParam Long idOrderDetail,
            @RequestParam Integer rating,
            @RequestParam String comment,
            @RequestPart(name = "feedbackImages", required = false) List<MultipartFile> images)
            throws ApplicationException {

        feedbackFacade.creat(idOrderDetail, rating, comment, images);

        return ResponseHandler.response("Đánh giá sản phẩm thành công");
    }

}
