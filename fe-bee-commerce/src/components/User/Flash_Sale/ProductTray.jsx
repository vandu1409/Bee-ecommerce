import React from "react";
import "./FlashSale.css";
import flashSaleImg from "../../../Images/flash-sale.png";
import Clock from "../Clock/Clock";
import ProductSale from "../ProductSale/ProductSale";
import { MdOutlineNavigateNext } from "react-icons/md";

const ProductTray = ({ countDown = "", isFlashSale = false, title = "Product best seller", products = [] }) => {
    return (
        <div className="pb-2 px-4 bg-tw-white shadow-sm rounded-lg mb-5">
            <div className="">
                <div className="flex">
                    <div className="w-100 flex align-items-center justify-content-between pt-3">
                        <div className="d-flex">
                            <div>{isFlashSale && <img src={flashSaleImg} alt="" />}</div>
                            <div>{title && <span className="font-bold text-xl ml-2">{title}</span>} </div>
                            <div>{countDown && <Clock target={countDown} />}</div>
                        </div>
                        <div className="flex items-center space-x-3 cursor-pointer text-[#1a94ff] font-bold">
                            <span>Xem tất cả</span>
                            <MdOutlineNavigateNext className="text-[20px]" />
                        </div>
                    </div>
                </div>
                <div className="content">
                    {products.map((item, index) => (
                        <ProductSale key={index} {...item} />
                    ))}
                    {
                        [1,2,3,4,5].map((item ) => <div key={item}></div>)
                    }
                </div>
            </div>
        </div>
    );
};

export default ProductTray;
