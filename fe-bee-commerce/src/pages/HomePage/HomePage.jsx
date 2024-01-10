import productsApi from "~/api/productsApi";
import Carausel from "../../components/User/Carausel/Carausel";
import ProductTray from "../../components/User/Flash_Sale/ProductTray";
import demo from "../../Images/no_cart.png";
import "./HomePage.scss";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";

function HomePage() {
    const { getAllProduct } = productsApi();
    const [listProduct, setListProduct] = useState([]);
    const [listBestSale, setListBestSale] = useState([])
    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(showLoader());
        getAllProduct()
            .then((response) => {
                setListProduct(response.data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
        getAllProduct()
            .then((response) => {
                setListBestSale(response.data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    }, []);

    return (
        <div className="w-100">
            <Carausel />
            <ProductTray key={1} title="Sản phẩm bán chạy" products={listProduct} />
            <ProductTray key={2} title="Gợi ý cho bạn" products={listBestSale} />
        </div>
    );
}

export default HomePage;
