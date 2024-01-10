import React from "react";
import {HiOutlineLockClosed} from "react-icons/hi";
import {useLocation, useNavigate} from "react-router-dom";
import Button from "../../components/common/Button";
import Input from "../../components/common/Input";
import Wrapper from "../../components/common/Wrapper";
import poster11 from "../../Images/poster_11.png";
import "./Login.css";
import authenticateApi from "~/api/authenticateApi";
import {NotificationManager} from "react-notifications";
import {hideLoader, showLoader} from "~/action/Loader.action";
import {useDispatch} from "react-redux";
import {useForm} from "react-hook-form";

const RestPassword = () => {
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const token = searchParams.get("token");
    const dispatch = useDispatch();
    const {resetPasswordApi } = authenticateApi();


    const {
        watch,
        register: registerForm,
        handleSubmit,
        formState: { errors },
    } = useForm();

    const navigate = useNavigate();

    const onSubmit = async (data) => {
        dispatch(showLoader());
        const newData = {
            ...data,
            token,
        };
        resetPasswordApi(newData)
            .then((response) => {
                navigate("/login");
                NotificationManager.success(response.data, "Thành công");
            })
            .catch((error) => {
                NotificationManager.error(error.message, "error");
            })
            .finally(() => {
                dispatch(hideLoader());
            });
    };
    return (
        <>
            <div className="w-100 max-w-[700px] mx-auto">
                <Wrapper>
                    <div className="row mx-0">
                        <div className="col-7 pl-8" style={{ marginTop: "20px" }}>
                            <div className="mb-3 text-center">
                                <h2 className="text-xl font-bold">Đặt lại mật khẩu</h2>
                            </div>
                            <form onSubmit={handleSubmit(onSubmit)} noValidate>
                                <Input
                                    messageClass="text-red-500"
                                    errorMessage={errors.password?.message}
                                    className="mb-2"
                                    leftIcon={<HiOutlineLockClosed />}
                                    isPassword={true}
                                    label="Password"
                                    name="password"
                                    inputRef={registerForm("password", {
                                        required: {
                                            value: true,
                                            message: "Password is required",
                                        },
                                        minLength: {
                                            value: 6,
                                            message: "Password must be at least 6 characters",
                                        },
                                        maxLength: {
                                            value: 16,
                                            message: "Password must be at most 16 characters",
                                        },
                                    })}
                                />

                                <Input
                                    messageClass="text-red-500"
                                    errorMessage={errors.confirmPassword?.message}
                                    className="mb-2"
                                    leftIcon={<HiOutlineLockClosed />}
                                    isPassword={true}
                                    label="Confirm Password"
                                    name="confirmPassword"
                                    inputRef={registerForm("confirmPassword", {
                                        required: {
                                            value: true,
                                            message: "Confirm Password is required",
                                        },
                                        validate: (value) => {
                                            if (watch("password") !== value) {
                                                return "Your passwords do no match";
                                            }
                                        },
                                    })}
                                />

                                <Button outline className="rounded-lg mb-3 w-100">
                                    Cập nhật mật khẩu
                                </Button>
                            </form>
                        </div>
                        <div className="col-md-5">
                            <img src={poster11} alt="" />
                        </div>
                    </div>
                </Wrapper>
            </div>
        </>
    );
};

export default RestPassword;
