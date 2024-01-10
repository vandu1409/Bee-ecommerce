package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.List;

import static com.beeecommerce.constance.ConstraintName.HASHTAG_NAME_UNIQUE;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hashtag",
        uniqueConstraints = {
                @UniqueConstraint(name = HASHTAG_NAME_UNIQUE, columnNames = {"name"})
        }
)
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<ProductTag> productTags = new ArrayList<>();

}