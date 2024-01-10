import axiosClient from "~/utils/axiosClient";

export default function WishlistApi() {
    return {
        createWishlist: (productId) => {
            return axiosClient.post("/wishlists", { productId });
        },
        getWishlist: (page = 0, size = 55) => {
            return axiosClient.get(`/wishlists?page=${page}&size=${size}`);
        },
    };
}
