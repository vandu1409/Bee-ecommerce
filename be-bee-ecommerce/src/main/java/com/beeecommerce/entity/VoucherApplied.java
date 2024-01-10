package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "voucher_apply") // Rename to voucher_apply
public class VoucherApplied {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount")
    private Long discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

}