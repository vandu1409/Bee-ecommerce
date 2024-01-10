package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "promotion_voucher")
public class PromotionVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

}