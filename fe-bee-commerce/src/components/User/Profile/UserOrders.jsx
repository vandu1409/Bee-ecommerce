import React, { useEffect, useState } from "react";
import Wrapper from "~/components/common/Wrapper";
import ListOrder from "./ListOrder";
import NoOrderNotification from "./NoOrderNotification";
import OrderApi from "~/api/orderApi";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";

const UserOrders = () => {
    const [tabs, setTabs] = useState({
        ["Tất cả"]: () => true,
        ["Chờ xác nhận"]: () => true,
        ["Chờ lấy hàng"]: () => true,
        ["Đang giao"]: () => true,
        ["Đã giao"]: () => true,
        ["Đã hủy"]: () => true,
    });
    const [orders, setOrders] = useState([]);
    const dispatch = useDispatch();

    const updateOrder = (updatedOrder) => {
        setOrders(updatedOrder);
    };

    useEffect(() => {
        dispatch(showLoader());
        orderHistoryUserLogged()
            .then((response) => {
                setOrders(response.data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    }, []);

    console.log(orders);

    const { orderHistoryUserLogged } = OrderApi();

    const [activeTab, setActiveTab] = useState("Tất cả");

    const tabArray = [<ListOrder />, <NoOrderNotification />];
    return (
        <>
            <Wrapper>
                <div className="mb-1">
                    <div className="row m-0">
                        <div className="flex items-center p-0">
                            {Object.keys(tabs).map((tab) => (
                                <div
                                    key={tab}
                                    className={`flex-1 text-center py-2 cursor-pointer ${
                                        activeTab === tab ? "active" : ""
                                    }`}
                                    onClick={() => setActiveTab(tab)}
                                >
                                    {tab}
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </Wrapper>
            <div className="flex flex-col-reverse">
                {orders.map((order) => (
                    <ListOrder key={order.id} order={order} />
                ))}
            </div>
        </>
    );
};

export default UserOrders;
