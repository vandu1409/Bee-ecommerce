package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import static com.beeecommerce.constance.ConstraintName.PRODUCT_TAG_PRODUCT_TAG_UNIQUE;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_tag", uniqueConstraints = {
        @UniqueConstraint(name = PRODUCT_TAG_PRODUCT_TAG_UNIQUE, columnNames = {"product_id", "tag_id"})
})
public class ProductTag {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Hashtag tag;

}