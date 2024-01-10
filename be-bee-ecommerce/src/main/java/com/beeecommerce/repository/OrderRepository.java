package com.beeecommerce.repository;

import com.beeecommerce.constance.OrderStatus;
import com.beeecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    //Tính tổng doanh thu của shop theo thang tùy chọn
    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.status = 'DELIVERED' AND o.user.id = ?1 AND MONTH(o.createAt) = ?2")
    double statisticalRevenueByMonth(Long userId, int monthCreateAt);

    //Tính tổng doanh thu của shop theo tháng hiện tại
    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.status = 'DELIVERED' AND o.user.id = ?1 AND MONTH(o.createAt) = MONTH(CURRENT_DATE)")
    double statisticalRevenueByCurrentMonth(Long userId);

    //Lấy tổng hóa đơn của shop theo tháng tùy chọn
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'DELIVERED' AND o.user.id = ?1 AND MONTH(o.createAt) = ?2")
    int totalOrderByMoth(Long userId, int monthCreateAt);

    //Lấy tổng hóa đơn của shop theo tháng hiện tại
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'DELIVERED' AND o.user.id = ?1 AND MONTH(o.createAt) = MONTH(CURRENT_DATE)")
    int totalOrderByCurrentMoth(Long userId);

    @Modifying
    @Query("UPDATE Order o SET o.status = ?2 WHERE o.id = ?1")
    void updateOrderStatus(Long id, OrderStatus status);



    @Query("select o from Order o where o.user.id=:id")
    Page<Order> findByUser(Long id, Pageable pageable);

    @Query("select DISTINCT  od.order from OrderDetail od where od.classify.group.product.vendors.id = :vendorsId")
    Page<Order> findByVendor(Long vendorsId, Pageable pageable);
}
