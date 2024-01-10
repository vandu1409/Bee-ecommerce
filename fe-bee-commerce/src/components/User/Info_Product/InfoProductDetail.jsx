import React from "react";
import "./InfoProduct.scss";
const InfoProductDetail = (product) => {
    console.log(product)
    return (
        <div className="w-100 p-4">
            <div className="mb-3">
                <h1 className="bg-[rgba(0,0,0,.02)] text-black font-medium p-2 text-lg">CHI TIẾT SẢN PHẨM</h1>
            </div>

            <div className="grid-cols-md-1 grid-cols- grid gap-y-2 gap-x-3">
                <div className="flex justify-between w-[300px]">
                    <span className="">
                        Thương hiệu
                    </span>
                    <div>{product.product.brand?.name}</div>
                </div>

                {product?.product?.attributes?.map((attr, index) => (
                    <div className="flex justify-between items-center w-[300px]" key={index}>
                        <span className="">
                            {attr.name}
                        </span>
                        <div className="">
                            {attr.value}
                        </div>

                    </div>
                ))}

            </div>
        </div>
    );
};

export default InfoProductDetail;
