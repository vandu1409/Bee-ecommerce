import Tippy from "@tippyjs/react/headless";
import avatarUrl from "~/Images/avatar.jpg";
import { useState } from "react";
import { AiOutlineLine } from "react-icons/ai";
import { BsBell } from "react-icons/bs";

import PopupMenu from "~/components/common/PopupMenu";
import logo from "~/Images/logo-v1.png";
import { MdOutlineNavigateNext } from "react-icons/md";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import { authSelector } from "~/selector/Auth.selector";

const topHeaderItemClass =
    "hover:text-blue-800 text-sm px-3  text-decoration-none my-3 hover:bg-blue-100 hover:rounded-md transition-all duration-300 ease-in-out";

const popupMenuData = [
    {
        name: "Thông tin tài khoản",
        link: "/account/profile",
    },
    {
        name: "Trang chủ",
        link: "/",
    },
    {
        name: "Đăng xuất",
        link: "/logout",
    },
];

function HeaderVendor({ breadcrumb = [] }) {
    const { accessToken, userData } = useSelector(authSelector);

    const [loggedIn, setLoggedIn] = useState(true);

    if (!userData) return;

    return (
        <div className="bg-blue-50 shadow-line position-sticky z-40">
            <div className="container py-3  flex justify-between">
                <div className="flex align-items-center">
                    <img className="w-[40px] mr-3" src={logo} alt="" />
                    {breadcrumb && breadcrumb.length > 0 ? (
                        <div className="flex align-items-center">
                            {breadcrumb.map(({ name, to }, index) => (
                                <Link to={to} className="flex align-items-center" key={index}>
                                    <span className="">{name}</span>
                                    {index !== breadcrumb.length - 1 && (
                                        <span className="text-2xl font-bold text-dark-900">
                                            <MdOutlineNavigateNext />
                                        </span>
                                    )}
                                </Link>
                            ))}
                        </div>
                    ) : (
                        <span className="text-2xl font-bold text-dark-900">Kênh người bán</span>
                    )}
                </div>
                <div className="flex align-items-center pr-7 ">
                    <Tippy
                        interactive
                        placement="bottom"
                        delay={[0, 300]}
                        render={({ ...attrs }) => (
                            <div {...attrs}>
                                <PopupMenu data={popupMenuData} />
                            </div>
                        )}
                    >
                        <div className="flex hover:bg-blue-200 bg-blue-50  p-[3px] text-dark-900 rounded-full align-items-center pl-5 transition-all duration-200">
                            <span className="text-md font-medium  transition-all">{userData.fullname}</span>
                            <img className="w-[35px] h-[35px] rounded-full ml-3" src={userData.avatar} alt="" />
                        </div>
                    </Tippy>
                    <div className="text-2xl">
                        <AiOutlineLine className="rotate-90 mx-2" />
                    </div>
                    <div className="text-2xl">
                        <BsBell />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default HeaderVendor;
