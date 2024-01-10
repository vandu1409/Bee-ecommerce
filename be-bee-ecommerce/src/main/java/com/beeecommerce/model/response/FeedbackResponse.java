package com.beeecommerce.model.response;

import com.beeecommerce.model.dto.FeedbackDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackResponse implements Serializable {
    private String imageAccount;

    private String nameAccount;

    private int rating;

    private LocalDate createAt;

    private List<String> imageFeedback;

    private String comment;

    private String classify;
    private String classifyGroup;

}
