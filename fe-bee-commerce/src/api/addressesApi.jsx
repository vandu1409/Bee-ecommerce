import axiosClient from "~/utils/axiosClient";

export default function addressesApi() {
    return {
        getProvince: function () {
            return axiosClient.get(`/addresses/provinces`);
        },
        getDistrict: function (provinceId) {
            return axiosClient.get(`/addresses/districts/${provinceId}`);
        },
        getWard: function (districtId) {
            return axiosClient.get(`/addresses/wards/${districtId}`);
        },
        getDetailWard: function (wardId) {
            return axiosClient.get(`/addresses/wards/detail/${wardId}`);
        },
        getDetailAddress: function (addressId) {
            return axiosClient.get(`/addresses/${addressId}`);
        },
        loadInitialAddress: function (wardId) {
            return axiosClient.get(`/addresses/wards/detail/${wardId}`).then(({ data }) => {
                return {
                    provinceId: data.province.id,
                    districtId: data.district.id,
                    wardId: data.ward.id,
                };
            });
        },

        createAddress: function (data) {
            return axiosClient.post(`/addresses`, data);
        },

        getAllAddress: function () {
            return axiosClient.get(`/addresses`);
        },
        calculateFee: (data) => {
            return axiosClient.post(`/GHN/calculate-shipping-fee`, data);
        }
    };
}
