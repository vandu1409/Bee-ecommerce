import logo from "~/Images/logo-v1.png";
import Tippy from "@tippyjs/react/headless";
import { LuSearch } from "react-icons/lu";
import { BsShop } from "react-icons/bs";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { LiaShoppingCartSolid, LiaUserSolid } from "react-icons/lia";
import { useContext, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import "./Header.scss";
import { logout } from "~/action/Auth.action";
import { userDataSelector } from "~/selector/Auth.selector";
import useLogout from "~/hooks/useLogout";

import { NotificationManager } from "react-notifications";
import searchapi from "~/api/searchapi";
import { hideLoader, showLoader } from "~/action/Loader.action";

import { keySearchSelector } from "~/selector/Search.selector";
import { setSearckKey } from "~/action/Search.action";

const suggestions = [
    "Điện thoại",
    "Laptop",
    "Váy",
    "Quần",
    "Áo",
    "Giày",
    "Đồng hồ",
    "Giày",
    "Gấu bông",
    "Vòng cổ",
    "Tất",
];

const topHeaderItemClass =
    "hover:text-blue-800 text-sm px-3  text-decoration-none my-3 hover:bg-blue-100 hover:rounded-md transition-all duration-300 ease-in-out";

function Header() {
    const logout = useLogout();
    const navigate = useNavigate();
    const userData = useSelector(userDataSelector);
    const dispatch = useDispatch();
    const keySearch = useSelector(keySearchSelector);
    const [key, setKey] = useState("");
    const [searckParams, setSearckParams] = useSearchParams();

    useEffect(() => {
        const uriKey = searckParams.get("keyword");
        setKey(uriKey);
        dispatch(setSearckKey(uriKey));
    }, []);

    const logoutHandler = () => {
        logout().then(() => {
            navigate("/login");
            NotificationManager.success("Đăng xuất thành công", "Thành công");
        });
    };
    const handleSearch = () => {
        dispatch(setSearckKey(key));
        navigate(`/search/?keyword=${key}`);
    };

    // const renderSearchSuggest = (attrs) => {
    //     return (
    //         <div className=" bg-white rounded-md shadow-md pb-2 w-[567px]" {...attrs}>
    //             <div className="flex flex-col ">
    //                 <span className="d-flex rounded-md justify-start align-item-center pl-4 py-2 hover:bg-blue-100">
    //                     <BsShop className="text-xl mr-3 text-blue-800 font-bold" />{" "}
    //                     <span className="mr-2">Tìm kiếm của hàng</span> <span className="text-blue-500">"{key}"</span>
    //                 </span>
    //                 {[1, 2, 3, 4, 5, 6].map((suggestion, index) => (
    //                     <span
    //                         className="px-2 w-100 text-sm rounded-md text-blue-500 hover:text-blue-800 hover:bg-blue-50 mb-2 cursor-pointer "
    //                         key={index}
    //                     >
    //                         Gới ý số {suggestion}
    //                     </span>
    //                 ))}
    //             </div>
    //         </div>
    //     );
    // };

    return (
        <div className="bg-blue-500 shadow-md position-sticky z-50 mb-5">
            <div className="container-fluid flex list-none align-item-center text-blue-50 ">
                <div className="container header-top">
                    <div className="row">
                        <div className="col">
                            <span className="menu-header px-5 flex justify-between">
                                <span>
                                    {userData?.role === "ADMIN" && (
                                        <span className={topHeaderItemClass}>
                                            <Link className="mx-0" to="/admin">
                                                Trang quản trị
                                            </Link>
                                        </span>
                                    )}
                                    {userData?.role === "VENDOR" && (
                                        <span className={topHeaderItemClass}>
                                            <Link className="mx-0" to="/vendor/products">
                                                Quản lý của hàng
                                            </Link>
                                        </span>
                                    )}
                                    {userData?.role === "CUSTOMER" && (
                                        <span className={topHeaderItemClass}>
                                            <Link className="mx-0" to="/register-vendor">
                                                Trở thành người bán Bee-Ecommerce
                                            </Link>
                                        </span>
                                    )}
                                    <span className={topHeaderItemClass}>
                                        <a className="mx-0" href="/">
                                            Tải ứng dụng
                                        </a>
                                    </span>
                                </span>
                                <span>
                                    <span className={topHeaderItemClass}>
                                        <a className="mx-0" href="/">
                                            <i className=" icon-header  fa-solid fa-bell" /> Thông báo
                                        </a>
                                    </span>
                                    <span className={topHeaderItemClass}>
                                        <a className="mx-0" href="/">
                                            <i className=" icon-header fa-solid fa-circle-question" />
                                            Hỗ trợ
                                        </a>
                                    </span>
                                    {!userData ? (
                                        <>
                                            <span className={topHeaderItemClass}>
                                                <Link className="mx-0 header-cart-list" to="/register">
                                                    Đăng ký
                                                </Link>
                                            </span>
                                            <span className={topHeaderItemClass}>
                                                <Link className="mx-0" to="/login">
                                                    Đăng nhập
                                                </Link>
                                            </span>
                                        </>
                                    ) : (
                                        <>
                                            <span className={topHeaderItemClass}>
                                                <Link className="mx-0" to="/account/profile">
                                                    {userData.fullname}
                                                </Link>
                                            </span>
                                            <span
                                                className={`cursor-pointer ${topHeaderItemClass}`}
                                                onClick={logoutHandler}
                                            >
                                                Đăng xuất
                                            </span>
                                        </>
                                    )}
                                </span>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <div className="container-fluid bg-blue-500 px-5 pt-4 pb-3">
                <div className="container">
                    <div
                        className=" flex justify-between align-items-start header-bottom position-relative"
                        style={{ paddingTop: 5 }}
                    >
                        <div className="col-2">
                            <Link className="w-100 flex" to="/">
                                <img src={logo} className="h-14 ml-6" alt="" />
                                <span className="text-3xl text-white font-bold ml-3 mt-1">Shop Bee</span>
                            </Link>
                        </div>

                        <div className="flex-grow-1 h-100">
                            <div className="h-11 flex px-3">
                                <div className="flex-1">
                                    <Tippy
                                        visible={key}
                                        appendTo="parent"
                                        interactive={true}
                                        // render={renderSearchSuggest}
                                        placement="bottom-start"
                                    >
                                        <input
                                            type="text"
                                            value={key}
                                            onChange={(e) => setKey(e.target.value)}
                                            className=" h-100 w-100 focus:outline-none px-4 rounded-s-xl"
                                            placeholder="Tìm kiếm sản phẩm"
                                        />
                                    </Tippy>
                                </div>
                                <button
                                    onClick={handleSearch}
                                    className="header-search-btn bg-blue-200 text-blue-900 px-4 rounded-e-xl text-lg"
                                >
                                    <LuSearch className="text-lg" />
                                </button>
                            </div>
                            <div className="w-100 flex flex-wrap pl-4 pr-20 overflow-hidden leading-5 text-sm h-5 mt-2">
                                {suggestions.map((suggestion, index) => (
                                    <a
                                        href={`/search/?keyword=${suggestion}`}
                                        className="mx-1 cursor-pointer text-blue-50 hover:text-blue-700"
                                        key={index}
                                    >
                                        {suggestion}
                                    </a>
                                ))}
                            </div>
                        </div>

                        <div className="header-cart mt-0 flex flex-1 align-items-center pr-10">
                            <Tippy
                                interactive
                                placement="bottom"
                                // render={(args) => (
                                //     <div className="header-cart-list" {...args}>
                                //         Giỏ hàng của bạn đang trống
                                //     </div>
                                // )}
                            >
                                <div
                                    className="mx-1 text-blue-50 hover:bg-blue-100 hover:text-blue-600 hover:rounded-full py-1 px-3 transition-all duration-200  ease-in-out rounded-none"
                                    badge={"99"}
                                >
                                    <Link to={"/cart"} className="flex align-items-center flex-col">
                                        <LiaShoppingCartSolid className="header-cart-icon min-w fa-sharp fa-solid fa-cart-shopping mt-0 text-3xl" />
                                        <span className="text-xs">Cart</span>
                                    </Link>
                                </div>
                            </Tippy>

                            {userData && (
                                <div className="header-top-cart mx-1 text-blue-50 hover:bg-blue-100 hover:text-blue-600 hover:rounded-full py-1 px-3 transition-all duration-200  ease-in-out rounded-none">
                                    <Link className="flex align-items-center flex-col">
                                        <LiaUserSolid className="header-cart-icon min-w fa-sharp fa-solid fa-cart-shopping mt-0 text-3xl" />
                                        {/*<span className="text-xs"> {tokenUser.sub}</span>*/}
                                        <div className="header-top-cart-list">
                                            <Link className="mx-0 " to="/login">
                                                Tài khoản của tôi
                                            </Link>

                                            <Link className="mx-0 " to="/login">
                                                Đơn mua
                                            </Link>

                                            <Link className="mx-0 " onClick={logoutHandler}>
                                                Đăng xuất
                                            </Link>
                                        </div>
                                    </Link>
                                </div>
                            )}
                            {/* // ) : (
                            //     <Tippy visible render={(attr) => <div className="" {...attr}></div>}>
                            //         <img className="w-[40px] h-[40px] rounded-full mx-3" src={avatarUrl} alt="Full name" />
                            //     </Tippy>
                            // )} */}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Header;
