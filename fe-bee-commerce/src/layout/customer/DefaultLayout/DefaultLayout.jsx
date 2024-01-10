import Footer from "../../../components/layout/customer/Footer/Footer";
import Header from "../../../components/layout/customer/Header/Header";

function DefaultLayout({ children }) {
    return (
        <>
            <Header />
            <div className="container mb-5">{children}</div>
            <Footer />
        </>
    );
}

export default DefaultLayout;
