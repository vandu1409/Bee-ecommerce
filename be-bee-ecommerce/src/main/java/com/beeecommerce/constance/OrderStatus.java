package com.beeecommerce.constance;

public enum OrderStatus {
    PENDING, // Đang chờ xử lý
    PAID,
    CONFIRMED, // Đã xác nhận, chờ người bán giao hàng
    DELIVERING, // Shipper đang giao hàng
    COMPLETED, // Đã giao hàng
    CANCELLED, // Đã hủy đơn hàng chỉ huỷ được khi PENDING
    RECEIVER_REJECTED, // Người nhận từ chối nhận hàng
    RETURNED, // Đã trả hàng



}
