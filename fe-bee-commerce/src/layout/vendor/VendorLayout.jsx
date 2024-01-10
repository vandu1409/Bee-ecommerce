import SidebarMenuVendor from "~/components/layout/vendor/SidebarMenuVendor/SidebarMenuVendor";
import HeaderVendor from "~/components/layout/vendor/HeaderVendor/HeaderVendor";
import { BsBox2 } from "react-icons/bs";
import { useSelector } from "react-redux";
import { userDataSelector } from "~/selector/Auth.selector";
import { SiPagespeedinsights } from "react-icons/si";
import { BiCategoryAlt } from "react-icons/bi";

const vendor = [
    {
        name: "Quản lý sản phẩm",
        icon: BsBox2,
        sub: [
            {
                name: "Danh sách sản phẩm",
                link: "/vendor/products",
            },
            {
                name: "Thêm sản phẩm",
                link: "/vendor/products/add",
            },
        ],
    },
    {
        name: "Quản lý đơn hàng",
        icon: BsBox2,
        sub: [
            {
                name: "Danh sách đơn hàng",
                link: "/vendor/order",
            },
        ],
    },
];

const admin = [
    {
        name: "Ngành hàng",
        icon: BiCategoryAlt,
        sub: [
            {
                name: "Danh sách",
                link: "/admin/categories/list",
            },
            {
                name: "Thêm mới",
                link: "/admin/categories/create",
            },
        ],
    },
    {
        name: "Thương hiệu",
        icon: BiCategoryAlt,
        sub: [
            {
                name: "Danh sách",
                link: "/admin/brand",
            },
        ],
    },
    {
        name: "Trang tĩnh",
        icon: SiPagespeedinsights,
        sub: [
            {
                name: "Quản lý trang tĩnh",
                link: "/admin/static-page",
            },
            {
                name: "Thêm mới",
                link: "/admin/static-page/new",
            },
        ],
    },
];

function VendorLayout({ children, dataSidebar = [] }) {
    const { role } = useSelector(userDataSelector);

    if (role === "VENDOR") {
        dataSidebar = vendor;
    } else if (role === "ADMIN") {
        dataSidebar = admin;
    }

    return (
        <div className="vh-100 relative">
            <div className="h-[73px] ">
                <HeaderVendor />
            </div>
            <div className="container-fluid px-0 h-[calc(100%-73px)]">
                <div className="flex h-100">
                    <div className="w-[215px] h-100">
                        <SidebarMenuVendor data={dataSidebar} />
                    </div>
                    <div className="flex-1 p-3 pt-4 overflow-y-scroll">
                        <div className="container">{children}</div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default VendorLayout;
