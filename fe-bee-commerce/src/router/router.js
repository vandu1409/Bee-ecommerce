import SideBarLayout from "../layout/customer/SideBarLayout/SideBarLayout";
import HomePage from "../pages/HomePage/HomePage";
import ForgotPassword from "../pages/LoginPage/ForgotPassword";
import LoginPage from "../pages/LoginPage/LoginPage";
import RegisterPage from "../pages/LoginPage/RegisterPage";
import ProductDetailPage from "../pages/ProductDetailPage/ProductDetailPage";
import VendorLayout from "../layout/vendor/VendorLayout";
import CreateProduct from "../pages/CreateProduct/CreateProduct";
import RestPassword from "~/pages/LoginPage/RestPassword";
import Cart from "~/pages/Cart/Cart";
import Demo from "~/pages/Demo/Demo";
import RegisterVendor from "~/pages/LoginPage/RegisterVendor";
import Shop from "~/pages/Shop/Shop";
import CreateCategory from "~/pages/Category/CreateCategory";
import ListCategory from "~/pages/Category/ListCategory";
import ProductManagement from "~/pages/Product/ProductManagement";
import ProfileLayout from "~/layout/customer/ProfileLayout/ProfileLayout";
import UserProfile from "~/components/User/Profile/UserProfile";
import UserOrders from "~/components/User/Profile/UserOrders";
import Checkout from "~/pages/Checkout/Checkout";
import CheckoutLayout from "~/layout/customer/CheckoutLayout/CheckoutLayout";
import ConformAccount from "~/pages/LoginPage/ConformAccount";
import StaticPageManager from "~/pages/StaticPage/StaticPageManager";
import StaticPageEdit from "~/pages/StaticPage/StaticPageEdit";
import StaticPagePreview from "~/pages/StaticPage/StaticPagePreview";
import StaticPage from "~/pages/StaticPage/StaticPage";
import ManagerOrder from "~/pages/ManagerOrder/ManagerOrder";
import ManagerOrderDetail from "~/pages/ManagerOrder/ManagerOrderDetail";
import Brand from "~/pages/Brand/Brand";
import OrderHistoryDetail from "~/components/User/Profile/OrderHistoryDetail";
import Liked from "~/components/User/Profile/Liked";
import ProductSearch from "~/pages/ProductSearch/ProductSearch";
import CategoryDetailPage from "~/pages/Category/CategoryDetailPage";
import VNPAYReturn from "~/pages/VnpayReturn";

export const publicRoutes = [
    { path: "/", element: <HomePage />, layout: SideBarLayout },
    { path: "/login", element: <LoginPage /> },
    { path: "/register", element: <RegisterPage /> },
    { path: "/detail/:selectedId", element: <ProductDetailPage /> },
    { path: "/forgot-password", element: <ForgotPassword /> },
    { path: "/reset-password", element: <RestPassword /> },
    { path: "/cart", element: <Cart /> },
    { path: "/demo", element: <Demo /> },
    { path: "/confirm-account", element: <ConformAccount /> },
    { path: "/shop/:id", element: <Shop /> },
    { path: "/static-page", element: <StaticPage /> },

    { path: "/search", element: <ProductSearch />, layout: SideBarLayout },
    { path: "/categories/:id/:name", element: <CategoryDetailPage /> },
    { path: "/payment/vnpay-return", element: <VNPAYReturn /> },
];

export const privateRoutes = [
    {
        prefix: "/account",
        role: "*",
        route: [
            { path: "/profile", element: <UserProfile />, layout: ProfileLayout },
            { path: "/ordered", element: <UserOrders />, layout: ProfileLayout },
            { path: "/orderedetail/:id", element: <OrderHistoryDetail />, layout: ProfileLayout },
            { path: "/checkout", element: <Checkout />, layout: CheckoutLayout },
            { path: "/liked", element: <Liked /> ,layout: ProfileLayout },
            { path: "/order", element: <ManagerOrder />, layout: VendorLayout },
            { path: "/orderdetail/:id", element: <ManagerOrderDetail />, layout: VendorLayout },
        ],
    },
    {
        prefix: "/admin",
        role: "ADMIN",
        route: [
            { path: "/", element: <h1>Trang chủ</h1>, layout: VendorLayout },

            { path: "/categories/create", element: <CreateCategory />, layout: VendorLayout },
            { path: "/categories/list", element: <ListCategory />, layout: VendorLayout },
            { path: "/categories/edit/:id", element: <CreateCategory />, layout: VendorLayout },

            { path: "/static-page", element: <StaticPageManager />, layout: VendorLayout },
            { path: "/static-page/new", element: <StaticPageEdit />, layout: VendorLayout },
            { path: "/static-page/preview", element: <StaticPagePreview /> },
            { path: "/brand", element: <Brand />, layout: VendorLayout },
        ],
    },
    {
        prefix: "/vendor",
        role: "VENDOR",
        route: [
            { path: "/", element: <h1>Vendor Home</h1>, layout: VendorLayout },
            { path: "/products", element: <ProductManagement />, layout: VendorLayout },
            { path: "/products/add", element: <CreateProduct />, layout: VendorLayout },
            { path: "/products/edit/:idProduct", element: <CreateProduct />, layout: VendorLayout },
            { path: "/order", element: <ManagerOrder />, layout: VendorLayout },
        ],
    },
    {
        role: "CUSTOMER",
        route: [
            { path: "/register-vendor", element: <RegisterVendor /> },
            { path: "/categories/list", element: <ListCategory />, layout: VendorLayout },
        ],
    },
];
