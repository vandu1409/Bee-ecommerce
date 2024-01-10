package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attributes")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Nationalized
    @Column(name = "name", length = 100)
    private String name;

    @OneToMany(mappedBy = "attributes")
    private List<CategoryAttribute> categoryAttributes = new ArrayList<>();

    @OneToMany(mappedBy = "attributes")
    private List<ProductAttribute> productAttributes = new ArrayList<>();

}