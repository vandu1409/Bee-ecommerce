import React, { createContext, useEffect, useState } from "react";
import "./Checkout.css";

import CheckoutOrderInfo from "./CheckoutOrderInfo";
import PaymentMethod from "./PaymentMethod";
import ShippingAddress from "./ShippingAddress";
import DiscountCode from "./DiscountCode";
import TotalOrder from "./TotalOrder.jsx";
import {useNavigate, useSearchParams} from "react-router-dom";
import {NotificationManager} from "react-notifications";

export const CheckoutContext = createContext({});

const Checkout = () => {
    const [checkoutData, setCheckoutData] = useState({});
    const navigate = useNavigate();
    const [selectAddressId, setSelectAddressId] = useState(9);

    useEffect(() => {
        const dataStr = sessionStorage.getItem("checkoutData");
        if (dataStr) {
            setCheckoutData(JSON.parse(dataStr));
        } else {
            NotificationManager.warning("Vui lòng chọn sản phẩm");
             navigate("/cart")
        }
    }, []);

    return (
        <CheckoutContext.Provider value={{ checkoutData, setCheckoutData, selectAddressId, setSelectAddressId }}>
            <div className="w-100 max-w-[1400px] mx-auto">
                <div className="row">
                    <div className="col-8 pr-0">
                        <CheckoutOrderInfo />
                        <PaymentMethod />
                    </div>
                    <div className="col-4">
                        <ShippingAddress />
                        <DiscountCode />
                        <TotalOrder />
                    </div>
                </div>
            </div>
        </CheckoutContext.Provider>
    );
};

export default Checkout;
