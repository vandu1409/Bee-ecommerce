import axiosClient from "~/utils/axiosClient";

export default function cartApi() {
    return {
        addToCart: (data) => {
            return axiosClient.post("/carts", data);
        },
        getCartByUserId: () => {
            return axiosClient.get("/carts");
        },
        updateCart: (data) => {
            return axiosClient.put("/carts", data);
        },
        deleteCart: (id) => {
            return axiosClient.delete(`/carts/${id}`);
        },
        countCart: () => {
            return axiosClient.get("/carts/count");
        },
        checkSameVendor: (data) => {
            return axiosClient.get("/carts/check?" + data.map(id => `ids=${id}`).join('&'));
        }
    };
}
