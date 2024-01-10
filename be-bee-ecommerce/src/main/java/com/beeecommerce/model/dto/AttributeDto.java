package com.beeecommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.beeecommerce.entity.Attribute}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttributeDto implements Serializable {
    private Long id;
    private String name;

    private String value;

    private boolean required;
}