import React from "react";
import "./ProductSale.css";
import genuineImage from "../../../Images/chinhHang.png";
import Tippy from "@tippyjs/react";
import "tippy.js/dist/tippy.css";
import { Link } from "react-router-dom";
import { toCurrency } from "~/utils/CurrencyFormatter";
import Star from "../../common/Star";

function ProductSale({
    image = "",
    rootPrice = 0,
    name = "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Dolorem odit autem culpa tempora totam ducimus! Ipsam ea accusantium aliquam iusto!",
    sold = 0,
    discount = 0,
    tag = "", // Tên tag của sản phẩm (Hot, Best seller, ...)
    colorTag = "#ee4b2b",
    poster = "",
    id = "",
    star = 0,
}) {
    return (
        <Link to={`/detail/${id}`}>
            <div>
                <div className="product-item ">
                    <div className="product-item-img flex items-center" >
                        <img src={poster} alt={name} className="w-100 object-fit-contain" />
                    </div>
                    <Tippy content={name} className="text-sm" delay={[300, 0]}>
                        <h4 className="product-item-title h-[32px]">{name}</h4>
                    </Tippy>
                    <div className="px-2 flex items-center space-x-3">
                        <div className="flex text-mini font-medium text-[rgba(0,0,0,.54)]">
                            {/*<span className="line-through">999.999.999đ</span>*/}
                        </div>
                        <span className="product-price !ms-0 text-[#ee4d2d]">{toCurrency(rootPrice)}</span>
                    </div>
                    <div className="flex items-center justify-between mt-2 px-2 mb-2">
                        {/* <Star className={`!text-[12px]`} point={star} /> */}
                        <span style={{ fontSize: 12, color: "rgba(0, 0, 0, 0.5)" }}>Đã bán {sold}</span>
                    </div>

                    <div className={`product-YeuThich px-2 text-xs text-[${colorTag}]`}>{tag}</div>
                    {/*<div className="product-Sale">*/}
                    {/*    <p className="product-giamGia">*/}
                    {/*        43% <span className="">Giảm</span>*/}
                    {/*    </p>*/}
                    {/*</div>*/}
                </div>
            </div>
        </Link>
    );
}

export default ProductSale;
