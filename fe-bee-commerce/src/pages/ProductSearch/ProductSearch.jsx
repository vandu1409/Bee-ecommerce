import React, { useEffect, useState } from "react";
import { NotificationManager } from "react-notifications";
import { useDispatch, useSelector } from "react-redux";
import { useLocation, useSearchParams } from "react-router-dom";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { setSearckKey } from "~/action/Search.action";
import searchapi from "~/api/searchapi";
import ProductTray from "~/components/User/Flash_Sale/ProductTray";
import { keySearchSelector } from "~/selector/Search.selector";

const ProductSearch = () => {
    const { search } = searchapi();
    const dispatch = useDispatch();
    const keyword = useSelector(keySearchSelector);
    const [dataProduct, setDataProduct] = useState([]);
    const [searckParams, setSearckParams] = useSearchParams();

    useEffect(() => {
        document.title = "Tìm kiếm";
        window.scrollTo(0, 0);
    }, []);

    useEffect(() => {
        setSearckParams({ keyword: keyword });
        dispatch(showLoader());
        search(keyword)
            .then((response) => {
                setDataProduct(response.data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    }, [keyword]);

    return (
        <div>
            <ProductTray key={1} title="Sản phẩm bán chạy" products={dataProduct} />
        </div>
    );
};

export default ProductSearch;
