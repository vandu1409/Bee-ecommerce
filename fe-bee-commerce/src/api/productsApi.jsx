import axiosClient from "~/utils/axiosClient";

export default function productsApi() {
    return {
        createProduct: (formData) => {
            return axiosClient.post("/products", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
        },
        // Lấy danh sách sản phẩm đầy đủ thông tin
        getAllProductsDetail: (currentPage = 0, size = 10) => {
            return axiosClient.get(`/products/detail?page=${currentPage}&size=${size}`);
        },
            getAllProduct: function () {
            return axiosClient.get(`/products/home/suggest`);
        },
        getProductById: function (id) {
            return axiosClient.get(`/products/${id}`);
        },
        getProductByCategoryId: function (id) {
            return axiosClient.get(`/products/home/product/category/${id}`);
        },

        getAllProductParentId: function (id) {
            return axiosClient.get(`/products/home/product/parentid/${id}`);
        },
        getSuggestProduct: function (id) {
            return axiosClient.get(`/products/find-similar/${id}?size=14`);
        }
    };
}
