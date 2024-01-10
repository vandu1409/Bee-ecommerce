package com.beeecommerce.entity;

import com.beeecommerce.constance.ScopeVoucher;
import com.beeecommerce.constance.TypeVoucher;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

import static com.beeecommerce.constance.ConstraintName.VOUCHER_CODE_UNIQUE;

@Getter
@Setter
@Entity
@Table(name = "voucher", uniqueConstraints = {
        @UniqueConstraint(name = VOUCHER_CODE_UNIQUE, columnNames = "code")
})
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "code", length = 20)
    private String code;

    @Nationalized
    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Nationalized
    @Column(name = "type", length = 100)
    @Enumerated(EnumType.STRING)
    private TypeVoucher type;

    @Column(name = "discount")
    private Long discount;

    @Column(name = "max_discount")
    private Long maxDiscount;

    @Nationalized
    @Column(name = "scope", length = 100)
    @Enumerated(EnumType.STRING)
    private ScopeVoucher scope;

    @Column(name = "min_apply")
    private Long minApply;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Account user;

    @Column(name = "description")
    private String description;

    @Override
    public String toString() {
        return "%s (%s)".formatted(title, code);
    }
}