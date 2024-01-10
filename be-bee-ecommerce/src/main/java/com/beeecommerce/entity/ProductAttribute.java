package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import static com.beeecommerce.constance.ConstraintName.PRODUCT_ATTRIBUTES_UNIQUE;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_attributes",
        uniqueConstraints = {
                @UniqueConstraint(name = PRODUCT_ATTRIBUTES_UNIQUE, columnNames = {"product_id", "attributes_id"})
        }
)
public class ProductAttribute {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attributes_id")
    private Attribute attributes;

    @Nationalized
    @Column(name = "\"value\"")
    private String value;

}