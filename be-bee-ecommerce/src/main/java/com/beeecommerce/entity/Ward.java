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
@Table(name = "ward")
public class Ward implements Locality {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id")
    private District district;

    @Nationalized
    @Column(name = "code", length = 50)
    private String code;

    @Nationalized
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "ward")
    private List<Address> addresses = new ArrayList<>();

    public Long getLongCode() {
        return Long.parseLong(code);
    }

}