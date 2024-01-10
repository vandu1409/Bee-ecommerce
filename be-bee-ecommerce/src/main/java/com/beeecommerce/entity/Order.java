package com.beeecommerce.entity;

import com.beeecommerce.constance.OrderStatus;
import com.beeecommerce.constance.PaymentMethod;
import com.beeecommerce.payment.VNPayPaymentRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order implements VNPayPaymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Account user;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "delivery_at")
    private LocalDate deliveryAt;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "shipping_fee")
    private Long shippingFee;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

//    @Column(name = "discount")
//    private Long discount;

    @Nationalized
    @Column(name = "notes", length = 200)
    private String notes;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Nationalized
    @Column(name = "payment_status", length = 1)
    private String paymentStatus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<VoucherApplied> vouchersApplied = new ArrayList<>();

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();


    public Long getDiscount(){

        if(this.getVouchersApplied().size() >0){
            return this.getVouchersApplied().stream().mapToLong(VoucherApplied::getDiscount).sum();
        }

        return 0l;
    }

    public Vendor getVendor(){
        return this.getOrderDetails().get(0).getClassify().getGroup().getProduct().getVendors();
    }

    @Override
    public Long getAmount() {
        return getTotalPrice() - getDiscount() + getShippingFee();
    }

    @Override
    public String getOrderInfo() {
        return "ORDER_" + id;
    }

    @Override
    public String getOrderType() {
        return "pay-order";
    }

    @Override
    public String getTxnRef() {
        return "ORDER_" + id;
    }

    @Override
    public boolean readyToPay() {
        return paymentMethod == PaymentMethod.VNPAY && status == OrderStatus.PENDING;
    }
}