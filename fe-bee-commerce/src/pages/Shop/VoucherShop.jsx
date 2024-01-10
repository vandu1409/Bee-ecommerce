import React from "react";

const VoucherShop = () => {
    return (
        <div className="bg-[#ebf9f9] p-3 w-[330px]  mt-2 ml-2  rounded-md">
            <div className="row flex items-center">
                <div className="col-9">
                    <div>
                        <span className="text-[17px] font-bold">Giảm 5%</span>
                    </div>
                    <div>
                        <p className="text-[15px] ">Đơn tối thiểu 239K Giảm tối đa 17K</p>
                    </div>
                    <div className="text-[#adb7b8] font-semibold  text-[14px]">Hết hạn sau 24h</div>
                </div>
                <div className="col-3">
                    <button className="bg-[#12c2c6] text-white font-bold text-[15px] px-3 py-2 rounded-md">
                        Lưu
                    </button>
                </div>
            </div>
        </div>
    );
};

export default VoucherShop;
