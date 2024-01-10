import React from "react";
import {AiOutlineExclamationCircle} from "react-icons/ai";

const ListVoucher = () => {
    return (
        <div>
            <div className="flex mt-3 space-x-2">
                <div className=" voucher_image">
                    <div className="">
                        <img
                            className="object-contain h-12 w-12"
                            src="https://salt.tikicdn.com/cache/128x128/ts/upload/b6/c1/66/74ae9fd52a0e96a63551e054c135b53e.png"
                            alt=""
                        />
                    </div>
                    <div>
                        <span>Ví zalo pay</span>
                    </div>
                </div>
                <div className=" w-full p-2 voucher_info">
                    <div className="flex">
                        <div>
                            <span>Giảm 10K</span>
                            <p>Cho đơn hàng từ 699K</p>
                        </div>
                        <div className="ml-auto">
                            <AiOutlineExclamationCircle style={{ color: "#017fff" }} />
                        </div>
                    </div>
                    <div className="flex mt-4 items-center">
                        <div>
                            <span>HSD: 30/11/23</span>
                        </div>
                        <div className="ml-auto">
                            <button
                                type="button"
                                style={{
                                    display: "block",
                                    color: "white",
                                    backgroundColor: "#017fff",
                                    fontWeight: "500",
                                    borderRadius: "4px",
                                    padding: "7px",
                                    width: "100px",
                                }}
                            >
                                Áp dụng
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ListVoucher;
