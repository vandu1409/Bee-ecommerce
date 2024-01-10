package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Nationalized
    @Column(name = "name")
    private String name;

    @Nationalized
    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "brand")
    private List<Product> products = new ArrayList<>();
}
