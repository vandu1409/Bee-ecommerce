package com.beeecommerce.service;

import com.beeecommerce.entity.Feedback;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.FeedbackDto;
import com.beeecommerce.model.response.FeedbackResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {

    Page<FeedbackResponse> getByProduct(Long idProd, Pageable pageable);

    void creat(FeedbackDto feedbackDto) throws ApplicationException;

}
