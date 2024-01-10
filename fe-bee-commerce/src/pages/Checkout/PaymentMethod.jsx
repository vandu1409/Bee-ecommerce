import React, { useContext, useRef } from "react";
import ThanhToanTienMat from "~/Images/icon-thanh-toan-tien-mat.png";
import ThanhToanVNpay from "~/Images/icon-thanh-toan-vnpay.png";
import Coin from "~/Images/coin.png";
import Wrapper from "~/components/common/Wrapper";
import { MdPayments } from "react-icons/md";
import { CheckoutContext } from "~/pages/Checkout/Checkout";
import { useSelector } from "react-redux";
import { userDataSelector } from "~/selector/Auth.selector";
import { toCurrency } from "~/utils/CurrencyFormatter";

const PaymentMethod = () => {
    const { checkoutData, setCheckoutData } = useContext(CheckoutContext);
    const { balance } = useSelector(userDataSelector);
    const paymentMethod = useRef("VNPAY");

    function handleChange(e) {
        const data = e.target.value;
        setCheckoutData({
            ...checkoutData,
            paymentMethod: data,
        });
    }

    function enoughBalance() {
        console.log(balance, checkoutData.totalAmount , checkoutData.totalDiscount, checkoutData.deliveryFee)
        return balance >= checkoutData.totalAmount - checkoutData.totalDiscount + checkoutData.deliveryFee;
    }

    console.log(enoughBalance())
    return (
        <div>
            <Wrapper className="mt-3">
                <div className="row p-3 m-0" style={{ backgroundImage: "linear-gradient(90deg,#ebf8ff,#fff)" }}>
                    <div className="flex items-center space-x-3">
                        <MdPayments className="w-[20px] h-[20px] text-[#4a90e2]" />
                        <span className="text-[16px] font-bold">Phương thức thanh toán</span>
                    </div>
                </div>

                <div className="row p-3 m-0">
                    <div className="flex items-center mb-3">
                        <input
                            id="default-radio-1"
                            type="radio"
                            value="COD"
                            onChange={handleChange}
                            name="payment-method"
                            className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300  dark:bg-gray-700 dark:border-gray-600"
                        />
                        <label
                            htmlFor="default-radio-1"
                            className="cursor-pointer ms-2 text-sm font-medium text-gray-900 dark:text-gray-300 flex items-center space-x-2"
                        >
                            <div>
                                <img src={ThanhToanTienMat} alt="" className="object-contain w-[40px] h-[40px]" />
                            </div>
                            <span>Thanh toán tiền mặt khi nhận hàng</span>
                        </label>
                    </div>
                    <div className="flex items-center mb-3">
                        <input
                            id="default-radio-2"
                            type="radio"
                            value="VNPAY"
                            onChange={handleChange}
                            name="payment-method"
                            className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300  dark:bg-gray-700 dark:border-gray-600"
                        />
                        <label
                            htmlFor="default-radio-2"
                            className="cursor-pointer ms-2 text-sm font-medium text-gray-900 dark:text-gray-300 flex items-center space-x-2"
                        >
                            <div>
                                <img src={ThanhToanVNpay} alt="" className="object-contain w-[40px] h-[40px]" />
                            </div>
                            <span>Thanh toán bằng VNPAY</span>
                        </label>
                    </div>
                    <label htmlFor="default-radio-3" className={`flex items-center mb-3  ${!enoughBalance() ? " opacity-70 cursor-not-allowed" : "cursor-pointer"}`}>
                        <input
                            id="default-radio-3"
                            type="radio"
                            value="BALANCE"
                            onChange={handleChange}
                            disabled={!enoughBalance()}
                            name="payment-method"
                            className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300  dark:bg-gray-700 dark:border-gray-600"
                        />
                        <div

                            className="cursor-pointer ms-2 text-sm font-medium text-gray-900 dark:text-gray-300 flex items-center space-x-2"
                        >
                            <div>
                                <img src={Coin} alt="" className="object-contain w-[40px] h-[40px]" />
                            </div>
                            <div>
                                <div className="mb-2">Dùng số dư tài khoảng</div>
                                <span className="text-md text-dark-700">{toCurrency(balance, false)} xu</span>
                            </div>
                        </div>
                    </label>
                </div>
            </Wrapper>
        </div>
    );
};

export default PaymentMethod;
