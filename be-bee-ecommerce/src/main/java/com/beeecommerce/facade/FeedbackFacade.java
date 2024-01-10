package com.beeecommerce.facade;

import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.FeedbackDto;
import com.beeecommerce.model.response.FeedbackResponse;
import com.beeecommerce.service.FeedbackService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackFacade {
    private final FeedbackService feedbackService;

    public Page<FeedbackResponse> getByProduct(Long idProd, Pageable pageable) throws ParamInvalidException {
        ObjectHelper.checkNullParam(idProd);

        return feedbackService.getByProduct(idProd, pageable);
    }

    public void creat(Long idOrderDetail, Integer rating, String comment, List<MultipartFile> images)
            throws ApplicationException {
        ObjectHelper.checkNullParam(idOrderDetail, rating, comment);

        FeedbackDto feedbackDto = FeedbackDto.builder()
                .comment(comment)
                .rating(rating)
                .orderDetailId(idOrderDetail)
                .imagesFeedback(images)
                .build();

        feedbackService.creat(feedbackDto);
    }
}
