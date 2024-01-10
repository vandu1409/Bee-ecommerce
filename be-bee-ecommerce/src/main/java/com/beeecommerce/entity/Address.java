package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Account user;

    @Nationalized
    @Column(name = "receiver_name", length = 100)
    private String receiverName;

    @Column(name = "receiver_phone", length = 15)
    private String receiverPhone;

    @Column(name = "street", length = 250)
    private String street;

    @Nationalized
    @Column(name = "note", length = 250)
    private String note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @OneToMany(mappedBy = "address")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "address")
    private List<Vendor> vendors = new ArrayList<>();

    public String getStringAddress() {
        return "%s %s %s %s".formatted(
                getStreet(),
                getWard().getName(),
                getWard().getDistrict().getName(),
                getWard().getDistrict().getProvince().getName()
        );

    }
}