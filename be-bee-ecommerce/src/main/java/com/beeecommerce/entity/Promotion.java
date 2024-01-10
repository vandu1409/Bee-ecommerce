package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(name = "name", length = 1)
    private String name;

    @Column(name = "start_at")
    private LocalDate startAt;

    @Column(name = "end_at")
    private LocalDate endAt;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "min_discount")
    private Long minDiscount;

    @Column(name = "category_id")
    private Long categoryId;

    @OneToMany(mappedBy = "promotion")
    private List<PromotionImage> promotionImages = new ArrayList<>();

    @OneToMany(mappedBy = "promotion")
    private List<PromotionProduct> promotionProducts = new ArrayList<>();

    @OneToMany(mappedBy = "promotion")
    private List<PromotionVoucher> promotionVouchers = new ArrayList<>();

}