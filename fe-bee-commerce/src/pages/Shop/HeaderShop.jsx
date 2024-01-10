import React, {useContext, useEffect, useState} from "react";
import { Link } from "react-router-dom";
import { FaCaretDown } from "react-icons/fa";
import { FiUsers } from "react-icons/fi";
import shop from "~/Images/shop+.png";
import { MdAddTask } from "react-icons/md";
import { BsFillChatDotsFill, BsChatLeftDots } from "react-icons/bs";
import { FaRegClock } from "react-icons/fa";
import { RiShoppingBag3Fill } from "react-icons/ri";
import { LuStore } from "react-icons/lu";
import Wrapper from "~/components/common/Wrapper";
import DetailShopInProductDetailApi from "~/api/DetailShopInProdDetail";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";
import {ShopInfo} from "~/pages/Shop/Shop";
const HeaderShop = (selectedId) => {

    const { vendor } = useContext(ShopInfo);

    return (
        <div className="w-100 max-w-[1200px] mx-auto ">
            <Wrapper className="">
                <div className="row p-3">
                    <div className="col  p-3">
                        <div className="flex space-x-7  items-center ">
                            <div className="flex space-x-2">
                                <div className="h-[72px] w-[72px]">
                                    <img
                                        className=" rounded-full object-contain"
                                        src={vendor?.image}
                                        alt="Avatar"
                                    />
                                </div>
                                <div className="flex justify-content-center">
                                    <div className="flex items-center space-x-2">
                                        <span className="text-[20px] text-[#0f1e29] font-bold">
                                            {vendor?.name}
                                        </span>
                                        <div className="h-[27px] w-[46px] mt-3">
                                            <img src={shop} alt="" className="object-contain" />
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div className="flex items-center">
                                {/* <div className="mt-2 flex items-center space-x-2">
                                    <button
                                        type="button"
                                        className=" p-2 rounded-sm flex items-center justify-center border border-[#e7e8ea] bg-[#e7e8ea]  text-[#3f4b53]  "
                                    >
                                        <MdAddTask className="" />
                                    </button>

                                    <button
                                        type="button"
                                        className="flex items-center justify-center border-[#e7e8ea] bg-[#e7e8ea]  text-[#3f4b53] p-2 rounded-sm "
                                    >
                                        <BsFillChatDotsFill className="" />
                                    </button>
                                </div> */}

                                <div className="grid grid-cols-3 border-l-2 border-[#e7e8ea] ml-[50px] pl-4 flex items-center space-x-9">
                                    <div className="">
                                        <div className="flex items-center space-x-2">
                                            <LuStore className="w-[24px] h-[24px]" />{" "}
                                            <span className="text-[#0f1e29]">
                                                Trở thành shop từ
                                            </span>
                                        </div>
                                        <div className=" text-[#ee6c0e] font-bold">
                                            {vendor?.registerAt?.reverse()?.join("/")}
                                        </div>
                                    </div>

                                    <div className="">
                                        <div className="flex items-center space-x-2">
                                            <RiShoppingBag3Fill className="w-[24px] h-[24px]" />{" "}
                                            <span className="text-[#0f1e29]">Sản phẩm</span>
                                        </div>
                                        <div className=" text-[#ee6c0e] font-bold">
                                            {vendor?.productCount}
                                        </div>
                                    </div>

                                    <div className="">
                                        <div className="flex items-center space-x-2">
                                            <FaRegClock className="w-[24px] h-[24px]" />{" "}
                                            <span className="text-[#0f1e29]">
                                                Địa chỉ cửa hàng
                                            </span>
                                        </div>
                                        <div className=" text-[#ee6c0e] font-bold">
                                            {vendor?.province}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </Wrapper>
        </div>
    );
};

export default HeaderShop;
