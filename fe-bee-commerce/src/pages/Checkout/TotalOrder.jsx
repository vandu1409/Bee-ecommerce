import React, { useContext, useEffect, useState } from "react";
import Wrapper from "~/components/common/Wrapper";
import { FaMoneyBillWave } from "react-icons/fa";
import { CheckoutContext } from "~/pages/Checkout/Checkout";
import { toCurrency } from "~/utils/CurrencyFormatter";
import shippingAddress from "~/pages/Checkout/ShippingAddress";
import { Button } from "react-bootstrap";
import { NotificationManager } from "react-notifications";
import checkoutOrderInfo from "~/pages/Checkout/CheckoutOrderInfo";
import checkoutApi from "~/api/checkoutApi";
import { useNavigate } from "react-router-dom";
import orderApi from "~/api/orderApi";
const TotalOrder = () => {
    const { checkoutData, selectAddressId, setSelectAddressId } = useContext(CheckoutContext);
    const { checkout } = checkoutApi();
    const { payOrder } = orderApi();
    const navigate = useNavigate();
    function handleCheckout() {
        // {
        //     "cartId": [
        //     12,13
        // ],
        //     "voucherApplied": [
        //     1
        // ],
        //     "addressId":10008,
        //     "paymentMethod":"COD"
        // }
        const data = {
            cartId: checkoutData.selectedItems?.map((item) => item.id),
            voucherApplied: checkoutData.voucherApplied,
            addressId: selectAddressId,
            paymentMethod: checkoutData.paymentMethod,
        };

        if (!data.paymentMethod) {
            NotificationManager.warning("Vui lòng chọn phương thức thanh toán");
            return;
        }

        checkout(data)
            .then(({ data }) => {
                NotificationManager.success("Đặt hàng thành công");
                sessionStorage.removeItem("checkoutData");

                if (data.paymentMethod === "VNPAY") {
                    payOrder(data.id)
                        .then(({ data }) => {
                            window.location.href = data;
                        })
                        .catch(() => {
                            NotificationManager.error("Chuyển hướng đến trang thanh toán thất bại");
                            navigate(`/account/orderedetail/${data.id}`);
                        });
                } else {
                    navigate(`/account/orderedetail/${data.id}`);
                }
            })
            .catch(({ data }) => {
                NotificationManager.error(data.message);
            });
    }

    return (
        <div>
            <Wrapper className="mt-3">
                <div className="row p-3 m-0" style={{ backgroundImage: "linear-gradient(90deg,#ebf8ff,#fff)" }}>
                    <div className="flex items-center space-x-3">
                        <FaMoneyBillWave className="w-[20px] h-[20px] text-[#4a90e2]" />
                        <span className="text-[16px] font-bold">Thanh toán</span>
                    </div>
                </div>
                <div className="p-4">
                    <div className="row">
                        <div className="border-b-2 border-[rgba(0,0,0,.09)] pb-3">
                            <div className="flex items-center justify-between">
                                <div>
                                    <span className="text-[#a495a1]">Tạm tính</span>
                                </div>
                                <div>
                                    <span>{toCurrency(checkoutData.totalAmount)}</span>
                                </div>
                            </div>
                            <div className="flex items-center justify-between">
                                <div>
                                    <span className="text-[#a495a1]">Phí vận chuyển</span>
                                </div>
                                <div>
                                    <span>{toCurrency(checkoutData.deliveryFee)}</span>
                                </div>
                            </div>
                        </div>
                        <div className="flex items-center justify-between mt-2">
                            <div>
                                <span className="text-[#3f4b53] text[14px]">Tổng thanh toán</span>
                            </div>
                            <div>
                                <span className="text-[20px] text-[#ee2624] font-bold">
                                    {toCurrency(
                                        checkoutData.totalAmount +
                                            checkoutData.deliveryFee -
                                            checkoutData.totalDiscount,
                                    )}
                                </span>
                            </div>
                        </div>
                        <div className="mt-2">
                            <Button className="w-100" onClick={handleCheckout}>
                                Đặt hàng
                            </Button>
                        </div>
                    </div>
                </div>
            </Wrapper>
        </div>
    );
};

export default TotalOrder;
