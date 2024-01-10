import React from "react";
import { Link } from "react-router-dom";
import logo from "~/Images/logo-v1.png";
import { BsFillTelephoneFill } from "react-icons/bs";
const HeaderLayout = () => {
    return (
        <div className="w-full bg-white p-3 mb-4">
            <div className="w-100 max-w-[1300px] mx-auto  ">
                <div className=" flex items-center justify-between">
                    <div className="flex items-center space-x-4">
                        <div>
                            <Link className="w-100 flex" to="/">
                                <img src={logo} className="h-14 ml-6" alt="" />
                                <span className="text-3xl text-blue-500 font-bold ml-3 mt-1">Shop Bee</span>
                            </Link>
                        </div>
                        <div
                            style={{
                                borderLeft: "1px solid rgb(26, 167, 255)",
                                color: "rgb(26, 167, 255)",
                                fontSize: "24px",
                                paddingLeft: "10px",
                            }}
                        >
                            Thanh toán
                        </div>
                    </div>
                    <div
                        className="flex items-center space-x-2"
                        style={{
                            borderRadius: "50px",
                            border: "1px solid #e6f4ff",
                            padding: "5px",
                            backgroundColor: "#f0f8ff",
                        }}
                    >
                        <div
                            style={{
                                borderRadius: "50px",

                                padding: "10px",
                                backgroundColor: "#19a7ff",
                            }}
                        >
                            <BsFillTelephoneFill style={{ color: "white" }} />
                        </div>
                        <div className="">
                            <div>
                                <span style={{ color: "#19a7ff", fontWeight: "bold" }}>0703414576</span>
                            </div>
                            <div>
                                <p
                                    style={{
                                        color: "#9fa1a9",
                                        fontSize: "13px",
                                        fontWeight: "500",
                                    }}
                                >
                                    8h - 21h, cả T7 cả CN
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default HeaderLayout;
