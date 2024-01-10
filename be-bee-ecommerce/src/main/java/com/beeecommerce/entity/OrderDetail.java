package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.beeecommerce.constance.ConstraintName.ORDER_DETAILS_ORDER_CLASSIFY_UNIQUE;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_details",
        uniqueConstraints = @UniqueConstraint(name = ORDER_DETAILS_ORDER_CLASSIFY_UNIQUE, columnNames = {"order_id", "classify_id"})
)
public class OrderDetail {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classify_id")
    private Classify classify;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "sell_price")
    private Long sellPrice;

    @Transient
    private Long totalPrice;

    @OneToMany(mappedBy = "orderDetails")
    private List<Feedback> feedbacks = new ArrayList<>();


    public Long getTotalPrice() {
        return this.quantity * this.sellPrice;
    }

    public Product getProduct(){
        return this.getClassify().getGroup().getProduct();
    }

    public boolean hasFeedback() {
        return feedbacks != null && !feedbacks.isEmpty();
    }
}