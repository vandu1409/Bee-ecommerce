import React, {useContext, useEffect, useState} from "react";
import Wrapper from "~/components/common/Wrapper";
import {GiShoppingBag} from "react-icons/gi";
import {CheckoutContext} from "~/pages/Checkout/Checkout";

const CheckoutOrderInfo = () => {
    const {checkoutData} = useContext(CheckoutContext);
    console.log("=======> CheckoutData", checkoutData.selectedItems);
    const [selectedItems, setSelectedItem] = useState(checkoutData);


    return (
        <div>
            <Wrapper className="">
                <div className="row p-3 m-0" style={{ backgroundImage: "linear-gradient(90deg,#ebf8ff,#fff)" }}>
                    <div className="flex items-center justify-between space-x-3">
                        <div className="flex">
                            <GiShoppingBag className="w-[20px] h-[20px] text-[#4a90e2]"/>
                            <span className="text-[16px] font-bold">Thông tin đơn hàng</span>
                        </div>
                        <span>{checkoutData.vendorName?.name}</span>
                    </div>
                </div>

                <div className="row p-3 m-0 font-semibold border-b-2 border-[rgba(0,0,0,.09)]">
                    <div className="col-6">
                        <span className=" text-lg text-black">Sản phẩm</span>
                    </div>
                    <div className="col-2 text-center">Đơn giá</div>
                    <div className="col-2 text-center">Số lượng</div>
                    <div className="col-2 text-center">Thành tiền</div>
                </div>

                {checkoutData?.selectedItems?.map((item) => (
                    <div key={item.id} className="row m-0 pl-4 p-2 mt-3 pb-3 border-b-2 border-[rgba(0,0,0,.09)]">
                        <div className="col-6">
                            <div className="flex  space-x-3">
                                <div className="">
                                    <img src={item.product.poster} alt="" className="w-[80px] h-[80px]"/>
                                </div>
                                <div className="">
                                    <div>
                                        <span className="product-cart-item-title ml-0 mb-0 text-[#27272A] text-[15px] hover:text-[#0B74E5]">
                                            {item.product.name}
                                        </span>
                                    </div>
                                    <div className="text-[#a5a1a0] text-[14px]">
                                        {item.classify.classifyValue} , {item.classify.classifyGroupValue}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="col-2 text-center">
                            <span className="text-[14px] text-[rgba(0,0,0,.87)] font-bold text-center">
                                {item.classify.price.toLocaleString("vi-VN", {
                                    style: "currency",
                                    currency: "VND",
                                    decimalDigits: 0,
                                    minimumFractionDigits: 0,
                                })}
                            </span>
                        </div>
                        <div className="col-2 text-center">
                            <span className="text-[14px] text-[rgba(0,0,0,.87)] font-bold">{item.quantity}</span>
                        </div>
                        <div className="col-2 font-bold text-center">
                            <span className="text-[#ee4d2d]">
                                {(item.classify.price * item.quantity).toLocaleString("vi-VN", {
                                    style: "currency",
                                    currency: "VND",
                                    decimalDigits: 0,
                                    minimumFractionDigits: 0,
                                })}
                            </span>
                        </div>
                    </div>
                ))}
            </Wrapper>
        </div>
    );
};

export default CheckoutOrderInfo;
