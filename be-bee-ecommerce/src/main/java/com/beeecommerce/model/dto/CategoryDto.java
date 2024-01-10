package com.beeecommerce.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * DTO for {@link com.beeecommerce.entity.Category}
 */
@Data
@Builder
@AllArgsConstructor
public class CategoryDto implements Serializable {

    private Long id;

    private Long parentId;


//    @JsonIgnore
//    private MultipartFile image;

    /**
     * Level của category sẽ được tạo dựa trên parentId và level của parent.
     * Nếu ko có parentId thì level sẽ là 1
     */
    private Integer level;

    private String name;

    private Boolean hasChildren;

    private String posterUrl;


}