import React from "react";
import {PropagateLoader as Loader} from "react-spinners";
import {useSelector} from "react-redux";
import {loaderSelector, loaderVisibleSelector} from "~/selector/Loader.seletor";

const PropagateLoader = () => {
    const isLoaderVisible = useSelector(loaderSelector);
    console.warn("isLoaderVisible", isLoaderVisible)
    if (isLoaderVisible.length === 0) return null;

    return (
        <div className="fixed top-0 left-0 z-[99999] w-screen h-screen bg-opacity-50 bg-black flex justify-center items-center">
            <Loader size={30} color="#36d7b7" />
        </div>
    );
};

export default PropagateLoader;
