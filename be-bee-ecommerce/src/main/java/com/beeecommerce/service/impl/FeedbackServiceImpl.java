package com.beeecommerce.service.impl;

import com.beeecommerce.constance.OrderStatus;
import com.beeecommerce.constance.TypeUpLoad;
import com.beeecommerce.entity.Account;
import com.beeecommerce.entity.Feedback;
import com.beeecommerce.entity.FeedbackImage;
import com.beeecommerce.entity.OrderDetail;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.mapper.FeedbackMapper;
import com.beeecommerce.model.dto.FeedbackDto;
import com.beeecommerce.model.response.FeedbackResponse;
import com.beeecommerce.repository.FeedbackImageRepository;
import com.beeecommerce.repository.FeedbackRepository;
import com.beeecommerce.repository.OrderDetailRepository;
import com.beeecommerce.service.AccountService;
import com.beeecommerce.service.AmazonClientService;
import com.beeecommerce.service.FeedbackService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackImageRepository feedbackImageRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final AmazonClientService amazonClientService;
    private final AccountService accountService;
    private final FeedbackMapper feedbackMapper;

    @Override
    public Page<FeedbackResponse> getByProduct(Long idProd, Pageable pageable) {

        return feedbackRepository
                .getByProduct(idProd, pageable)
                .map(feedbackMapper);
    }

    @Override
    public void creat(FeedbackDto feedbackDto) throws ApplicationException {

        OrderDetail orderDetail = orderDetailRepository.findById(feedbackDto.getOrderDetailId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Không tìm thấy chi tiết sản phẩm này!"));

        System.err.println(orderDetail.getId());

        OrderStatus orderStatus = orderDetail.getOrder().getStatus();

        Account account = accountService.getAuthenticatedAccount();

        if (orderStatus != OrderStatus.COMPLETED) {
            throw new ApplicationException("Đơn hàng này chưa được giao!");
        }

        if (account.getId() != orderDetail.getOrder().getUser().getId()) {
            throw new ApplicationException("Bạn không có quyền đánh giá sản phẩm này!");
        }
        System.err.println("TRÍ LÊ" + feedbackDto.getImagesFeedback().size());
        List<String> listUrlImages = new ArrayList<>();
        for (MultipartFile image : feedbackDto.getImagesFeedback()) {
            String url = amazonClientService.uploadFile(image, TypeUpLoad.IMAGE);
            System.out.println(url + "jasj");
            listUrlImages.add(url);
        }

        Feedback feedback = Feedback.builder()
                .id(feedbackDto.getId())
                .createAt(LocalDate.now())
                .rating(feedbackDto.getRating())
                .comment(feedbackDto.getComment())
                .orderDetails(orderDetail)
                .build();

        feedbackRepository.save(feedback);

        FeedbackImage feedbackImg = new FeedbackImage();
        for (String url : listUrlImages) {

            feedbackImg.setFeedback(feedback);
            feedbackImg.setUrl(url);

            feedbackImageRepository.save(feedbackImg);
        }
    }

}
