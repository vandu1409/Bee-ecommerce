import axiosClient from "~/utils/axiosClient";

export default function DetailShopInProductDetailApi() {
    return {
        getShopDetailInProduct: (selectedId) => {
            return axiosClient.get(`/products/find-vendors/${selectedId}`)
        }
    };
}
