import React from "react";
import Wrapper from "~/components/common/Wrapper";
import { PiTicketBold } from "react-icons/pi";
const DiscountCode = () => {
    return (
        <div>
            <Wrapper className="mt-3">
                <div
                    className="row p-3 m-0"
                    style={{ backgroundImage: "linear-gradient(90deg,#ebf8ff,#fff)" }}
                >
                    <div className="flex items-center space-x-3">
                        <PiTicketBold className="w-[20px] h-[20px] text-[#4a90e2]" />
                        <span className="text-[16px] font-bold">Mã ưu đãi của Shop Bee</span>
                    </div>
                </div>
                <div className="row p-3">
                    <button className="border-1 border-dashed border-[#cfd2d4] p-2 text-[#0f62fe] font-semibold">
                        Chọn/Nhập mã
                    </button>
                </div>
            </Wrapper>
        </div>
    );
};

export default DiscountCode;
