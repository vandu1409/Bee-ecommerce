package com.beeecommerce.entity;

import com.beeecommerce.constance.ConstraintName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cart",
        uniqueConstraints = {
                @UniqueConstraint(name = ConstraintName.CART_USER_ID_CLASSIFY_ID_UNIQUE, columnNames = {"user_id", "classify_id"})
        }
)
public class Cart {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Account user;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "selected_item")
    private Boolean selectedItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classify_id")
    private Classify classify;

    public Product getProduct() {
        return classify.getGroup().getProduct();
    }

}