package com.beeecommerce.document;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "products")
public class ProductDocument {
    private Long id;
    private String name;
    private Long price;
    @Field(type = FieldType.Keyword)
    private String brandname;

    @Field(type = FieldType.Keyword)
    private String categoryname;
    private String tags;
}
