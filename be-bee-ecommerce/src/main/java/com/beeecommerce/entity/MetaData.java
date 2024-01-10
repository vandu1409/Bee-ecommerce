package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "meta_data", uniqueConstraints = {@UniqueConstraint(name = "meta_data_unique", columnNames = "_key")})
public class MetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "_key", length = 100)
    private String key;

    @Nationalized
    @Column(name = "_value", length = 100)
    private String value;

    @Nationalized
    @Column(name = "description", length = 100)
    private String description;
}
