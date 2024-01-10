package com.beeecommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {
    private Long id;

    private Long orderDetailId;

    private String comment;

    private int rating;

    private List<MultipartFile> imagesFeedback;
}
