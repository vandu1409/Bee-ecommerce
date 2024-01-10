import React, {createContext, useEffect, useState} from "react";
import demo from "../../Images/no_cart.png";
import ProductTray from "~/components/User/Flash_Sale/ProductTray";
import CarauselProduct from "./CarauselProduct";
import bgGiaTot from "~/Images/bg-gia-tot.png";
import Wrapper from "~/components/common/Wrapper";
import CarauselVoucher from "./CarauselVoucher";
import HeaderShop from "./HeaderShop";
import ProductFilter from "./ProductFilter";
import { useParams } from "react-router-dom";
import DetailShopInProductDetailApi from "~/api/DetailShopInProdDetail";
import vendorApi from "~/api/vendorApi";

export const ShopInfo = createContext({});
const mockProducts = [
    {
        image: demo,
        name: "Túi đeo chéo nam thời trang ",
        price: 1,
        sold: 100,
        discount: 0,
        tag: "Hot",
    },
    {
        image: demo,
        name: "Túi đeo chéo nam thời trang",
        price: 99999999,
        sold: 100,
        discount: 10,
        tag: "Hot",
    },
    {
        image: demo,
        name: "Túi đeo chéo nam thời trang",
        price: 999999999,
        sold: 100,
        discount: 10,
        tag: "Hot",
    },
    {
        image: demo,
        name: "Túi đeo chéo nam thời trang",
        price: 12,
        sold: 100,
        discount: 10,
        tag: "Hot",
    },
    {
        image: demo,
        name: "Túi đeo chéo nam thời trang",
        price: 123,
        sold: 100,
        discount: 10,
        tag: "Hot",
    },
    {
        image: demo,
        name: "Túi đeo chéo nam thời trang",
        price: 12312,
        sold: 100,
        discount: 10,
        tag: "Hot",
    },
    {
        image: demo,
        name: "Túi đeo chéo nam thời trang",
        price: 1234213,
        sold: 100,
        discount: 10,
        tag: "Hot",
    },
    {
        image: demo,
        name: "Túi đeo chéo nam thời trang",
        price: 3412341234,
        sold: 100,
        discount: 10,
        tag: "Hot",
    },
    {
        image: demo,
        name: "Túi đeo chéo nam thời trang",
        price: 131231,
        sold: 100,
        discount: 10,
        tag: "Hot",
    },
    {
        image: demo,
        name: "Túi đeo chéo nam thời trang",
        price: 1233,
        sold: 100,
        discount: 10,
        tag: "Hot",
    },
    {
        image: demo,
        name: "Túi đeo chéo nam thời trang",
        price: 999999999,
        sold: 100,
        discount: 10,
        tag: "Hot",
    },
    {
        image: demo,
        name: "Túi đeo chéo nam thời trang",
        price: 999999999,
        sold: 100,
        discount: 10,
        tag: "Hot",
    },
];
const Shop = () => {
    const { id } = useParams();
    const [vendor, setVendor] = useState({});
    const [products, setProducts] = useState()
    const {getVendorById, getProductByVendorId} =  vendorApi();

    useEffect(() => {
        getVendorById(id)
            .then(({data}) => {
                setVendor(data)
            })
        getProductByVendorId(id)
            .then(({data}) => {
                setProducts(data)
            })
    }, []);

    return (
        <ShopInfo.Provider value={{
            vendor
        }}>
            <HeaderShop />

            {/*<div className="w-100 max-w-[1200px] mx-auto mt-3 ">*/}
            {/*    <Wrapper className="p-4">*/}
            {/*        <h4 className="font-bold">MÃ GIẢM GIÁ CỦA SHOP</h4>*/}
            {/*        <CarauselVoucher />*/}
            {/*    </Wrapper>*/}
            {/*</div>*/}

            {/*<div*/}
            {/*    className="w-100 max-w-[1200px]  mx-auto mt-3 p-3  shadow-border rounded-md"*/}
            {/*    style={{*/}
            {/*        backgroundImage: `url(${bgGiaTot})`,*/}
            {/*        backgroundRepeat: "no-repeat",*/}
            {/*        backgroundSize: "cover",*/}
            {/*    }}*/}
            {/*>*/}
            {/*    <h4 className="font-bold text-[20px] text-white">Giá siêu tốt</h4>*/}
            {/*    <CarauselProduct mockProducts={mockProducts} />*/}
            {/*</div>*/}

            {/*<div className="w-100 max-w-[1200px]  mx-auto mt-3 ">*/}
            {/*    <Wrapper className="p-3">*/}
            {/*        <h4 className="font-bold text-[20px]">Hàng mới về</h4>*/}
            {/*        <CarauselProduct mockProducts={mockProducts} />*/}
            {/*    </Wrapper>*/}
            {/*</div>*/}

            <div className="w-100 max-w-[1200px] mx-auto mt-3 ">
                <ProductTray key={2} title="Danh sách sản phẩm" products={products} />
            </div>

            {/*<ProductFilter mockProducts={mockProducts} />*/}
        </ShopInfo.Provider>
    );
};

export default Shop;
