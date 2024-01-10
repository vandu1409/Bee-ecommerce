package com.beeecommerce.entity;

import com.beeecommerce.constance.ConstraintName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category_attributes",
        uniqueConstraints = {
                @UniqueConstraint(name = ConstraintName.CATEGORY_ATTRIBUTES_CATEGORY_UNIQUE, columnNames = {"category_id", "attributes_id"})
        }
)
public class CategoryAttribute {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attributes_id")
    private Attribute attributes;

    @Column(name = "required")
    private Boolean required;

}