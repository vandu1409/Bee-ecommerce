import { Link } from "react-router-dom";
import image403 from "~/Images/Image403.png";
import "./Error404Page.css";
function Error403Page() {
    return (
        <>
            <div className="h-screen w-screen wrapper-error404 flex justify-center items-center">
                <div className="flex items-center space-x-14">
                    <div className="flex-col items-center mr-7">
                        <h1 className="title-error text-center">403 error</h1>
                        <p className="content-error text-center">
                            The page you are looking for was <br /> moved, removed, renamed, or <br /> never have
                            existed.
                        </p>
                        <div className="flex justify-center">
                            <Link
                                href="/"
                                className="button-go-homepage"
                                to={"/"}
                            >
                                Go to homepage
                            </Link>
                        </div>
                    </div>

                    <div className="w-[550px] h-[450px]">
                        <img className="w-full h-full" src={image403} alt="Error 404" />
                    </div>
                </div>
            </div>
        </>
    );
}

export default Error403Page;
