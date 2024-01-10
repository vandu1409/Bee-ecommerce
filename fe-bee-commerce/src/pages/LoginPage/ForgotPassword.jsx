import React from "react";
import {HiOutlineUser} from "react-icons/hi";
import {Link, useNavigate} from "react-router-dom";
import Button from "../../components/common/Button";
import Input from "../../components/common/Input";
import Wrapper from "../../components/common/Wrapper";
import poster11 from "../../Images/poster_11.png";
import "./Login.css";
import authenticateApi from "~/api/authenticateApi";
import {NotificationManager} from "react-notifications";
import {useDispatch} from "react-redux";
import {hideLoader, showLoader} from "~/action/Loader.action";
import {useForm} from "react-hook-form";

const ForgotPass = () => {
    const { forgot_passwordApi } = authenticateApi();
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const {
        register: registerForm,
        handleSubmit,
        formState: { errors },
    } = useForm();

    const onSubmit = async (data) => {
        dispatch(showLoader());
        forgot_passwordApi(data)
            .then((response) => {
                navigate("/");
                dispatch(hideLoader());
                NotificationManager.success(response.data, "Thành công");
            })
            .catch((error) => {
                dispatch(hideLoader());
                NotificationManager.error(error.message, "Lỗi");
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
                                <h2 className="text-xl font-bold">Quên mật khẩu</h2>
                            </div>
                            <form onSubmit={handleSubmit(onSubmit)} noValidate>
                                <Input
                                    message="Đường dẫn đặt lại mật khẩu sẽ được gửi tới email bạn đã đăng ký"
                                    label="Username"
                                    placeholder="Username"
                                    className="mb-3"
                                    name="username"
                                    leftIcon={<HiOutlineUser />}
                                    errorMessage={errors.username?.message}
                                    messageClass="text-red-500"
                                    inputRef={registerForm("username", {
                                        required: { value: true, message: "Username is required" },
                                        pattern: {
                                            value: /^[a-zA-Z0-9_]+$/,
                                            message: "Username is invalid",
                                        },
                                    })}
                                />

                                <Button outline className="rounded-lg mb-3 w-100">
                                    Đặt lại mật khẩu
                                </Button>
                            </form>
                            <div className="text-center text-dark-700">
                                <p>
                                    Bạn chưa có tài khoảng
                                    <Link className="text-blue-500 font-bold ml-1" to={`/register`}>
                                        Đăng ký ngay !
                                    </Link>
                                </p>
                            </div>
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

export default ForgotPass;
