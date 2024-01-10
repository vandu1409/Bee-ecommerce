package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.beeecommerce.constance.ConstraintName.WISHLIST_ACCOUNT_PRODUCT_UNIQUE;

@Getter
@Setter
@Entity
@Table(name = "wishlist",
        uniqueConstraints = {
                @UniqueConstraint(name = WISHLIST_ACCOUNT_PRODUCT_UNIQUE, columnNames = {"user_id", "product_id"}),
        }
)
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Account user;

}