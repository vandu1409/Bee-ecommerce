package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.beeecommerce.constance.ConstraintName.VENDOR_ACCOUNT_UNIQUE;

@Getter
@Setter
@Entity
@Table(name = "vendors",
        uniqueConstraints = {
                @UniqueConstraint(name = VENDOR_ACCOUNT_UNIQUE, columnNames = "user_id")
        }
)
public class Vendor {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Account user;

    @Column(name = "register_at")
    private LocalDate registerAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "vendors")
    private List<Product> products = new ArrayList<>();



}