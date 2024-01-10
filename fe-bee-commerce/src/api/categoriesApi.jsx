import axiosClient from "~/utils/axiosClient";

export default function categoriesApi() {
    return {
        getFirstCategories: function () {
            return axiosClient.get("/categories/level/1");
        },
        getChildrenCategories: function (parentId) {
            return axiosClient.get(`/categories/children/${parentId}`);
        },
        createCategory: function (formData) {
            return axiosClient.post("/categories", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
        },
        getAllCategory: function (currentPage) {
            return axiosClient.get(`/categories?page=${currentPage}`);
        },
        getCategoryById: function (id) {
            return axiosClient.get(`/categories/${id}`);
        },
        UpdateCategory: function (id, formData) {
            return axiosClient.put(`/categories/${id}`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
        },

        deleteCategory: function (id) {
            return axiosClient.delete(`/categories/${id}`);
        },

        getAllCategoryInproduct: function () {
            return axiosClient.get(`/categories/home/allCategory`);
        },
        getCategoryByparentid: function (id) {
            return axiosClient.get(`/categories/home/allCategory/${id}`);
        },
    };
}
