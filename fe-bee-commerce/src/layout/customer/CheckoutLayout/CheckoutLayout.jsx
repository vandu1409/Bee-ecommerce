import HeaderLayout from "~/components/layout/customer/Header/HeaderLayout";
import Footer from "../../../components/layout/customer/Footer/Footer";

function CheckoutLayout({ sidebar = <></>, children }) {
    return (
        <>
            <HeaderLayout />
            <div className="container-fluid mb-5 ">
                <div className="container mb-5">{children}</div>
            </div>
            <Footer />
        </>
    );
}

export default CheckoutLayout;
