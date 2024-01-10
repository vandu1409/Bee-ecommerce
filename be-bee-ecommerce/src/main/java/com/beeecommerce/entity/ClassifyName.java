package com.beeecommerce.entity;

import com.beeecommerce.constance.DefaultValue;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.util.List;

import static com.beeecommerce.constance.ConstraintName.CLASSIFY_NAME_NAME_UNIQUE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "classify_name", uniqueConstraints = {
        @UniqueConstraint(name = CLASSIFY_NAME_NAME_UNIQUE, columnNames = {"name"})
})
public class ClassifyName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(name = "name", length = 100)
    private String name = DefaultValue.SPECIAL_NAME;

    @OneToMany(mappedBy = "classifyName")
    private List<ClassifyGroup> classifyGroups;

    @OneToMany(mappedBy = "classifyName")
    private List<Classify> classifies;

}
