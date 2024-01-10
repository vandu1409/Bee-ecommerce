package com.beeecommerce.entity;

import com.beeecommerce.constance.DefaultValue;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classify", uniqueConstraints = {
        @UniqueConstraint(name = "classify_name_id_value_unique", columnNames = {"classify_name_id", "value", "group_id"})
}
)
public class Classify {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private ClassifyGroup group;

    @Nationalized
    @Column(name = "\"value\"", length = 100)
    private String value = DefaultValue.SPECIAL_VALUE;

    @Column(name = "sell_price")
    private Long sellPrice;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "inventory", nullable = false)
    private Long inventory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "classify_name_id")
    private ClassifyName classifyName;

    @OneToMany(mappedBy = "classify")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "classify")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Long getQuantitySold() { // Chưa check order có trạng thái là success hay chưa
        return this.getOrderDetails().stream().mapToLong(OrderDetail::getQuantity).sum();
    }

    public String getName() {
        return this.getClassifyName() == null ? null : this.getClassifyName().getName();
    }

}