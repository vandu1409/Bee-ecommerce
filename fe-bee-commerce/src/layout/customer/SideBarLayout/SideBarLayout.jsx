import categoriesApi from "~/api/categoriesApi";
import Footer from "../../../components/layout/customer/Footer/Footer";
import Header from "../../../components/layout/customer/Header/Header";
import CategoriesBar from "../../../components/User/CategoriesBar/CategoriesBar";
import { useEffect, useState } from "react";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";
import { useDispatch } from "react-redux";

function SideBarLayout({ sidebar = <></>, children }) {

    return (
        <>
            <Header />
            <div className="container-fluid mb-5">
                <div className="container">
                    <div className="row">
                        <div className="col-2">
                            <CategoriesBar />
                        </div>
                        <div className="col-9 px-3">{children}</div>
                    </div>
                </div>
            </div>
            <Footer />
        </>
    );
}

export default SideBarLayout;
