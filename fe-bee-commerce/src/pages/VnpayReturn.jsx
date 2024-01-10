import Wrapper from "~/components/common/Wrapper";
import { IoCheckmarkDone } from "react-icons/io5";
import { useSearchParams } from "react-router-dom";
import { useEffect, useState } from "react";
import vnPayApi from "~/api/vnPayApi";
import {toCurrency} from "~/utils/CurrencyFormatter";
import {convertDate} from "~/utils/Helper";

function VnpayReturn({ children }) {
    const { checksum } = vnPayApi();

    const [param, setParam] = useSearchParams();

    const [vnPayParam, setVnPayParam] = useState({});

    useEffect(() => {
        const newVnPayParam = {};
        for (let [key, value] of param) {
            newVnPayParam[key] = value;
        }

        checksum(newVnPayParam).then((res) => {
            setVnPayParam(newVnPayParam);
        });
    }, []);

    return (
        <Wrapper className="px-4 py-5 w-100 max-w-[500px] mx-auto">
            <div className="flex justify-center">
                <div className="">
                    <div className="flex justify-center mb-3">
                        <IoCheckmarkDone className="text-9xl text-green-600" />
                    </div>
                    <div className="font-bold text-3xl">Thanh toán thành công</div>
                    <div className="py-4">
                        <div className="flex justify-between">
                            <div className="font-semibold">Mã đơn hàng:</div>
                            <div className="font-semibold">{vnPayParam.vnp_BankTranNo}</div>
                        </div>
                        <div className="flex justify-between">
                            <div className="font-semibold">Thời gian:</div>
                            <div className="font-semibold">{convertDate(vnPayParam.vnp_PayDate)}</div>
                        </div>
                        <div className="flex justify-between">
                            <div className="font-semibold">Số tiền:</div>
                            <div className="font-semibold">{toCurrency(Number(vnPayParam.vnp_Amount) / 100)}</div>
                        </div>
                        <div className="flex justify-between">
                            <div className="font-semibold">Trạng thái:</div>
                            <div className="font-semibold">Thành công</div>
                        </div>
                    </div>
                </div>
            </div>
        </Wrapper>
    );
}

export default VnpayReturn;
