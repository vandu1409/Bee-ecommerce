import axiosClient from "~/utils/axiosClient";

export default function brandsApi() {
    return {
        getBrands: () => {
            return axiosClient.get("/brands");
        },
        createBrand: function (formData) {
            return axiosClient.post("/brands", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
        },
        getAllBrand: function (currentPage) {
            return axiosClient.get(`/brands?page=${currentPage}`);
        },
        getBrandById: function (id) {
            return axiosClient.get(`/brands/${id}`);
        },
        UpdateCategory: function (id, formData) {
            return axiosClient.put(`/brands/${id}`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
        },

        deleteCategory: function (id) {
            return axiosClient.delete(`/brands/${id}`);
        },
    };
}
