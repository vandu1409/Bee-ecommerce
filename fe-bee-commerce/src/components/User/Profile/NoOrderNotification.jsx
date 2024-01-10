import React from "react";
import imgNotification from "~/Images/thongbao-k-data.png";
const NoOrderNotification = () => {
    return (
        <div className="row h-[400px] text-center m-0 bg-white p-3">
            <div className="flex items-center justify-center">
                <div className="flex-col ">
                    <div className="mb-2">
                        <img
                            src={imgNotification}
                            alt=""
                            className="h-[100px] w-[100px] object-fill ml-4"
                        />
                    </div>
                    <div>
                        <span className="text-[18px] text-[rgba(0,0,0,.8)] font-semibold ">
                            Chưa có đơn hàng
                        </span>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default NoOrderNotification;
