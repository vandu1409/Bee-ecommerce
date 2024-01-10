package com.beeecommerce.entity;

import com.beeecommerce.model.core.Locality;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.List;

import static com.beeecommerce.constance.ConstraintName.DISTRICT_NAME_UNIQUE;

@Getter
@Setter
@Entity
@Table(name = "district",
        uniqueConstraints = {
                @UniqueConstraint(name = DISTRICT_NAME_UNIQUE, columnNames = {"name", "province_id"})
        }
)
public class District implements Locality {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "province_id")
    private Province province;

    @Column(name = "code", length = 50)
    private String code;

    @Nationalized
    @Column(name = "name", length = 100)
    private String name;

    @OneToMany(mappedBy = "district")
    private List<Ward> wards = new ArrayList<>();

}