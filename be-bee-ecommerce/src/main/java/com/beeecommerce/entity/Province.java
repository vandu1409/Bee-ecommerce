package com.beeecommerce.entity;

import com.beeecommerce.model.core.Locality;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "province")
public class Province implements Locality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 100)
    private String code;

    @Nationalized
    @Column(name = "name", length = 100)
    private String name;

    @OneToMany(mappedBy = "province")
    private List<District> districts = new ArrayList<>();

}