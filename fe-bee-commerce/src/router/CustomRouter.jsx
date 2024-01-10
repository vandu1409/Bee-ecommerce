// noinspection JSValidateTypes

import {Route, Routes} from "react-router-dom";
import {privateRoutes, publicRoutes} from "~/router/router";
import Error403Page from "~/pages/ErrorPage/Error403Page";
import Error404Page from "~/pages/ErrorPage/Error404Page";
import {Fragment, useEffect} from "react";
import DefaultLayout from "~/layout/customer/DefaultLayout/DefaultLayout";
import {useSelector} from "react-redux";
import {userDataSelector} from "~/selector/Auth.selector";


function getLayoutComponent(layout) {
    let LayoutComponent;
    if (layout) {
        LayoutComponent = layout;
    } else if (layout === null) {
        LayoutComponent = Fragment;
    } else {
        LayoutComponent = DefaultLayout;
    }
    return LayoutComponent;
}


function CustomRouter({children}) {
    const userData = useSelector(userDataSelector);
    return (
        <Routes>
            {publicRoutes.map((route, index) => {
                const LayoutComponent = getLayoutComponent(route.layout);

                return (
                    <Route
                        key={index}
                        path={route.path}
                        element={<LayoutComponent>{route.element}</LayoutComponent>}
                    />
                );
            })}

            {privateRoutes.map(({ route, role, prefix= "" }) => {
                return route.map(({ element, path, layout }) => {
                    if (!role) {
                        console.warn(`Not found role for [${prefix}]! Please check your route config!`);
                        return;
                    }

                    let LayoutComponent = getLayoutComponent(layout);

                    path = path === "/" ? "" : path;

                    let permission = false;

                    if (!userData?.role) { // Bắt buộc đăng nhập
                        permission = false;
                    } else if (Array.isArray(role)) { // kiểm tra nếu role là một mảng các role
                        permission = role.find((r) => r === userData.role);
                    } else if (typeof role === "string") { // nếu role là string thì trung role mới cho đăng nhập
                        permission = role === "*" || userData?.role === role;
                    }

                    return (
                        <Route
                            key={path}
                            path={prefix + path}
                            element={
                                permission ? <LayoutComponent>{element}</LayoutComponent> : <Error403Page />
                            }
                        />
                    );
                });
            })}

            <Route path="*" element={<Error404Page />} />
        </Routes>
    );
}

export default CustomRouter;