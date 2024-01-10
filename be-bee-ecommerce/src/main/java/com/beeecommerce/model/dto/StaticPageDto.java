package com.beeecommerce.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaticPageDto {
    private Long id;
    private String path;
    private String content;
    private String title;

}
