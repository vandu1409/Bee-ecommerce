import React, { useEffect, useState } from "react";
import "./ProductReview.css";
import Star from "~/components/common/Star";

const ProductReview = ({ feedback }) => {
    return (
        <div className="row p-4">
            <h5 className="text-black text-lg font-bold mb-4 pl-0">ĐÁNH GIÁ SẢN PHẨM</h5>

            {feedback.map((item) => (
                <div className="flex space-x-3 mt-2 ml-2 border-b border-[rgba(0,0,0,.09)] pb-[30px]">
                    <div className="h-17 w-16">
                        <img className=" rounded-full object-cover" src={item.imageAccount} alt="" />
                    </div>

                    <div>
                        <span className="text-[15px] text-[#000] font-bold">{item.nameAccount}</span>
                        <div className="flex space-x-1 mb-1">
                            <Star className={`!text-[15px]`} point={item.rating} />
                        </div>
                        <span className="text-[rgba(0,0,0,.54)] text-[13px]">
                            {item.createAt.join("-")} | Phân loại hàng: {item.classify}, {item.classifyGroup}
                        </span>
                        <div>
                            <p className="text-sm text-[rgba(0,0,0,.87)] leading-5 whitespace-pre-wrap mr-4 mb-2">
                                {item.comment}
                            </p>
                        </div>
                        <div className="img-review">
                            {item.imageFeedback.map((img) => (
                                <div className="img-review-item" key={img.id}>
                                    <img className="" src={img} alt="" />
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default ProductReview;
