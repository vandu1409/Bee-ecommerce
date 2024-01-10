import React from "react";
import demo from "../../Images/no_cart.png";

import { AiOutlineUnorderedList, AiFillCaretRight } from "react-icons/ai";
import ProductSale from "~/components/User/ProductSale/ProductSale";

const ProductFilter = ({ mockProducts }) => {
    return (
        <div className="w-100 max-w-[1200px] mx-auto">
            <div className="flex">
                <div className="w-[260px] pr-2">
                    <div className="bg-white p-4">
                        <div className="mb-3">
                            <div className="flex items-center py-2">
                                <AiOutlineUnorderedList className="mr-[10px] " />
                                <h4 className="  text-[rgb(56,56,61)] text-[17px] font-bold m-0 px-0 ">
                                    Tất cả danh mục
                                </h4>
                            </div>
                            <div className="pt-1">
                                <div className="row flex items-center cursor-pointer mb-2">
                                    <div className="col-1">
                                        <AiFillCaretRight className="text-[#ee4d2d]" />
                                    </div>
                                    <div className="col-9">
                                        <span className="active-category ">Thiết bị điện tử</span>
                                    </div>
                                </div>
                                <div className="row flex items-center cursor-pointer mb-2">
                                    <div className="col-1"></div>
                                    <div className="col-9">
                                        <span className="category-item-title">Phụ kiệm tivi</span>
                                    </div>
                                </div>
                                <div className="row flex items-center mb-2">
                                    <div className="col-1"></div>
                                    <div className="col-9">
                                        <span className="">Máy game console</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="w-[1100px] ml-3 ">
                    <div className="bg-white  flex p-3 ">
                        <div className="search-query-active">
                            <span>Phổ biến</span>
                        </div>
                        <div className="search-query">
                            <span>Bán chạy</span>
                        </div>
                        <div className="search-query">
                            <span>Giá cao đến thấp</span>
                        </div>
                        <div className="search-query">
                            <span>Giá thấp đến cao</span>
                        </div>
                    </div>

                    <div className="row m-0">
                        {mockProducts.map((item, index) => (
                            <div className="col-2 p-0">
                                <ProductSale key={index} {...item} />
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProductFilter;
