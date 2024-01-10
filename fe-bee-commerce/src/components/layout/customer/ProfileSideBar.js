import { Link, useLocation } from "react-router-dom";
import { FaPlus, FaRegUser } from "react-icons/fa6";
import { useSelector } from "react-redux";
import { userDataSelector } from "~/selector/Auth.selector";
import defaultAvatar from "~/Images/avatar.jpg";
import Wrapper from "~/components/common/Wrapper";
import { MdContentPaste } from "react-icons/md";
import { toCurrency } from "~/utils/CurrencyFormatter";
import { GoHeartFill } from "react-icons/go";
import {useState} from "react";
import DepositModal from "~/components/Modal/DepositModal";

const sideBarData = [
    {
        title: "Tài khoản của tôi",
        icon: FaRegUser,
        path: "/account/profile",
    },
    {
        title: "Đơn mua",
        icon: MdContentPaste,
        path: "/account/ordered",
    },
    {
        title: "Đã thích",
        icon: GoHeartFill,
        path: "/account/liked",
    },
];

function ProfileSideBar({ children }) {
    const location = useLocation();
    const { avatar = defaultAvatar, fullname, balance } = useSelector(userDataSelector);
    const [showDeposit, setShowDeposit] = useState(false);

    return (
        <div className="w-100 max-w-[1300px] mx-auto mb-5">
            <div className="flex gap-3">
                <div className="w-[320px] h-100 ">
                    <Wrapper className="px-3 py-4">
                        <div className="flex space-x-4 mb-10">
                            <div className="w-[54px] shrink">
                                <img
                                    className=" h-[54px] w-[54px] rounded-full object-contain border"
                                    src={avatar || defaultAvatar}
                                    alt=""
                                />
                            </div>
                            <div className="flex flex-col flex-1]">
                                <div className="">
                                    <span className="text-[16px] text-[#333] font-bold leading-4">{fullname}</span>
                                </div>
                                <Link
                                    to="/account/profile"
                                    className="flex items-center space-x-2 text-xs text-blue-600 font-bold"
                                >
                                    {toCurrency(balance, false)} xu
                                </Link>
                                <span
                                    className="text-xs flex items-center text-dark-700 gap-2 font-bold hover:text-dark-800"
                                    onClick={() => setShowDeposit(true)}
                                >
                                    <span>Nạp thêm</span>
                                    <FaPlus />
                                </span>
                            </div>
                        </div>
                        <div className="mt-4">
                            {sideBarData.map(({ path, title, icon: Icon }) => (
                                <Link to={path} key={path}>
                                    <div className="flex items-center space-x-3 cursor-pointer p-2 rounded-3 bg-blue-500 bg-opacity-0 hover:!bg-opacity-10 transition-opacity duration-300">
                                        <Icon className="text-xl" />
                                        <span className={`uppercase  font-semibold text-[14px]`}>{title}</span>
                                    </div>
                                </Link>
                            ))}
                        </div>
                    </Wrapper>
                </div>
                <div className="flex-1">
                    {children}
                </div>
                <DepositModal show={showDeposit} setter={setShowDeposit} />
            </div>
        </div>
    );
}

export default ProfileSideBar;
