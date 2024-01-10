import React from "react";
import "./Error404Page.css";
import { Link } from "react-router-dom";
import image404 from "~/Images/image404.png";
const Error404Page = () => {
    return (
        <div className="h-screen w-screen wrapper-error404 flex justify-center items-center">
            <div className="flex items-center space-x-14">
                <div className="flex-col items-center mr-7">
                    <h1 className="title-error text-center">404 error</h1>
                    <p className="content-error text-center">
                        The page you are looking for was <br /> moved, removed, renamed, or <br /> never have existed.
                    </p>
                    <div className="flex justify-center">
                        <Link
                            href="https://website97860.nicepage.io/Page-2.html#"
                            className="button-go-homepage"
                            to={"/"}
                        >
                            Go to homepage
                        </Link>
                    </div>
                </div>

                <div className="w-[550px] h-[450px]">
                    <img className="w-full h-full" src={image404} alt="Error 404" />
                </div>
            </div>
        </div>
    );
};

export default Error404Page;
