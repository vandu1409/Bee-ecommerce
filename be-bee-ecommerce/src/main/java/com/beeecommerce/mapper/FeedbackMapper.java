package com.beeecommerce.mapper;

import com.beeecommerce.entity.Feedback;
import com.beeecommerce.model.response.FeedbackResponse;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class FeedbackMapper implements Function<Feedback, FeedbackResponse> {

    @Override
    public FeedbackResponse apply(Feedback feedback) {
        String nameAccount = feedback.getOrderDetails().getOrder().getUser().getFullname();
        String imageAccount = feedback.getOrderDetails().getOrder().getUser().getAvatar();
        String classify = feedback.getOrderDetails().getClassify().getValue();
        String classifyGroup = feedback.getOrderDetails().getClassify().getGroup().getValue();

        return FeedbackResponse.builder()
                .rating(feedback.getRating())
                .createAt(feedback.getCreateAt())
                .comment(feedback.getComment())
                .imageAccount(imageAccount)
                .imageFeedback(feedback.getAllImageFeedback())
                .nameAccount(nameAccount)
                .classify(classify)
                .classifyGroup(classifyGroup)
                .build();
    }
}
