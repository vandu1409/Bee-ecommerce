import React, { useEffect, useState } from "react";
import { BsFillChatDotsFill } from "react-icons/bs";
import { IoStorefront } from "react-icons/io5";
import "./InfoProductShop.css";
import { Link, useParams } from "react-router-dom";
import { useDispatch } from "react-redux";
import DetailShopInProductDetailApi from "~/api/DetailShopInProdDetail";
import addressesApi from "~/api/addressesApi";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";
import { differenceInDays, format } from 'date-fns';
const InfoProductShop = () => {
    const { selectedId } = useParams();

    const { getShopDetailInProduct } = DetailShopInProductDetailApi();
    const { getDetailWard } = addressesApi();
    const dispatch = useDispatch();

    const [shopDetail, setShopDetail] = useState({});
    const [addresses, setAddresses] = useState({});
    const [registerAtArray, setRegisterAtArray] = useState([]);

    useEffect(() => {
        dispatch(showLoader());
        getShopDetailInProduct(selectedId)
            .then((response) => {
                setShopDetail(response.data);
                setRegisterAtArray(response.data?.registerAt || ["2023", "12", "18"])
                //call api addresses
                getDetailWard(response.data.wardId)
                    .then((res) => {
                        setAddresses(res.data)
                    })
                    .catch((error) => {
                        NotificationManager.error(error.data.message, "Lỗi");
                    })
                //end address
            })
            .catch((error) => {
                NotificationManager.error(error.data?.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    }, [selectedId]);

    const registerDate = new Date(registerAtArray[0], registerAtArray[1] - 1, registerAtArray[2]);
    const currentDate = new Date();
    const daysDifference = differenceInDays(currentDate, registerDate);

    return (
        <>

            <div className="p-3 flex gap-2" style={{ borderRight: "3px solid #fbfbfb" }}>
                <div className="flex items-center space-x-2">
                    <img
                        className="rounded-circle w-[64px] h-[64px]"
                        src={shopDetail.image}
                        alt="Avatar"
                    />

                    <div>
                        <div className="mb-2">
                            <span className=" block text-black font-normal text-base">
                                {shopDetail?.name}
                            </span>
                            <span className="text-[14px] text-[rgba(0,0,0,.54)]">{addresses.province?.name}</span>
                        </div>


                    </div>
                </div>
                <div className="flex items-center space-x-2">
                    <Link
                        to={`/shop/${shopDetail.id}`}
                        type="button"
                        className="w-[100%] rounded-sm p-[5px] text-[15px] bg-[#e7e8ea] text-[#3f4b53] rounded-lg border-1 border-[#e7e8ea] flex justify-center items-center "
                    >
                        <IoStorefront className="text-[16px] " />
                        <span className="ml-2">Xem shop</span>
                    </Link>
                </div>
            </div>
            <div className="flex flex-col justify-content-center items-end info-basic-shop col-5 pr-5">
                <div className="info-basic-shop-item">
                    <label>Sản phẩm</label>
                    <span>{shopDetail.productCount}</span>
                </div>

                <div className="info-basic-shop-item">
                    <label>Tham gia</label>
                    <span>{daysDifference} ngày</span>
                </div>
            </div>

        </>
    );
};

export default InfoProductShop;
