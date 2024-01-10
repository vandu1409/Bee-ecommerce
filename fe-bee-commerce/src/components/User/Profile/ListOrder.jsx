import React, { useEffect, useState } from "react";
import { IoChatboxEllipses } from "react-icons/io5";
import { LuStore } from "react-icons/lu";
import { LiaShippingFastSolid } from "react-icons/lia";
import { IoShieldCheckmark } from "react-icons/io5";
import Wrapper from "~/components/common/Wrapper";
import { toCurrency } from "~/utils/CurrencyFormatter";
import { Link } from "react-router-dom";
import orderApi from "~/api/orderApi";
import { NotificationManager } from "react-notifications";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import {getOrderStatus} from "~/utils/Helper";

const ListOrder = ({ order }) => {
    const { cancelClientOrder } = orderApi();
    const dispatch = useDispatch();

    const onclickHandlerCancel = (id) => {
        dispatch(showLoader());
        cancelClientOrder(id)
            .then((response) => {
                NotificationManager.success(response.data, "Thông báo");
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    };

    return (
        <>
            <Wrapper className="mt-3">
                <div className="row m-0 bg-white p-3">
                    <div className="row border-b-2 border-[#f1f1f1] pb-3">
                        <div className="flex items-center justify-between">
                            <div className="flex items-center space-x-2">
                                <div className="text-[14px] font-bold">{order?.shopName}</div>
                                <div className="flex items-center space-x-2">

                                    <Link
                                        to={`/shop/${order.shopId}`}
                                        type="button"
                                        className=" space-x-1 py-[4px] px-[8px] flex items-center justify-center rounded-sm   border border-[#e7e8ea] bg-[#fff]  text-[#3f4b53] text-[12px]"
                                    >
                                        <LuStore className="" /> <span>Xem shop</span>
                                    </Link>
                                </div>
                            </div>
                            <div className="flex items-center space-x-3">
                                <div className="text-[#40b2a3] font-semibold flex items-center space-x-2 border-r-2 border-[#ececec] px-2">
                                    <LiaShippingFastSolid />
                                    <span>Trạng thái đơn hàng</span>
                                </div>

                                <div>
                                    <span className="text-[#ff424e] text-[14px] font-semibold">
                                        {getOrderStatus(order)}
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    {order?.orderDetailsDto.map((orderDetail) => (
                        <Link to={`/account/orderedetail/${order.id}`}>
                            <div className="row mt-3">
                                <div className="flex items-center justify-between border-b-2 border-[#f1f1f1] pb-3">
                                    <div className="flex space-x-2">
                                        <div className="h-[80px] w-[80px] border border-[#f1f1f1]">
                                            <img
                                                src={orderDetail.classifyDto.image}
                                                alt=""
                                                className="object-contain"
                                            />
                                        </div>
                                        <div className="flex flex-col">
                                            <div>
                                                <span className="text-[rgba(0,0,0,.87)] text-[16px]">
                                                    {orderDetail?.productName}
                                                </span>
                                            </div>
                                            <div>
                                                <span className="text-[rgba(0,0,0,.54)] text-[14px]">
                                                    Phân loại hàng: {orderDetail?.classifyDto?.classifyValue} ,{" "}
                                                    {orderDetail.classifyDto.classifyGroupValue}
                                                </span>
                                            </div>
                                            <div>
                                                <span className="text-[rgba(0,0,0,.87)] text-[14px]">
                                                    {" "}
                                                    Số lượng: {orderDetail?.quantity}
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                    <div>
                                        <div>
                                            <span className="text-[#ee4d2d] ml-2">
                                                {toCurrency(orderDetail?.classifyDto?.price)}
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </Link>
                    ))}
                    <div className="row my-3 ">
                        <div className=" flex justify-end items-center text-[rgba(0,0,0,.8)] text-[14px]">
                            <IoShieldCheckmark className="text-[#ff424e] text-[20px] mr-2" />
                            Thành tiền:
                            <span className="text-[#ff424e] text-[24px] ml-3">{toCurrency(order?.amount)}</span>
                        </div>
                    </div>
                    <div className="row mt-2">
                        <div
                            className=" space-x-3 flex justify-end items-center text-[#fff] text-[14px]"
                            onClick={() => onclickHandlerCancel(order?.id)}
                        >
                            {order?.status === "PENDING" || order?.status === "PAID" ? (
                                <button className="bg-[#ff424e] py-[8px] px-[60px] rounded-sm">HỦY ĐƠN</button>
                            ) : (
                                ""
                            )}
                        </div>
                    </div>
                </div>
            </Wrapper>
        </>
    );
};

export default ListOrder;
