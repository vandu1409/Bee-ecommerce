import axiosClient from "~/utils/axiosClient";

export default function orderApi() {
    return {
        getAllOrder: () => {
            return axiosClient.get("/orders/find-vendor-logged?size=20");
        },

        getOrderDetail: (id) => {
            return axiosClient.get(`/orders/find/${id}`);
        },
        changeStatus: (id) => {
            return axiosClient.put(`/orders/change-status/${id}`);
        },
        cancelOrder: (id) => {
            return axiosClient.delete(`/orders/admin/cancel/${id}`);
        },
        orderHistoryUserLogged: () => {
            return axiosClient.get("/orders/find-user-logged", {});
        },
        orderHistoryUserLoggedDetail: (id) => {
            return axiosClient.get(`/orders/find/${id}`);
        },
        cancelClientOrder: (id) => {
            return axiosClient.delete(`/orders/client/cancel/${id}`);
        },
        payOrder: (id) => {
            return axiosClient.post(`/checkout/payment-vnpay/${id}`);
        },
        completeOrder: (id) => {
            return axiosClient.put(`/orders/client/confirmed/${id}`);
        },

    };
}
