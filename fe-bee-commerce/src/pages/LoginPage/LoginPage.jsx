import { Link, useNavigate, useSearchParams } from "react-router-dom";
import "./Login.css";
import { HiOutlineLockClosed, HiOutlineUser } from "react-icons/hi";
import Input from "../../components/common/Input";
import Wrapper from "../../components/common/Wrapper";
import Button from "../../components/common/Button";
import poster11 from "../../Images/poster_11.png";
import { NotificationManager } from "react-notifications";
import authenticateApi from "~/api/authenticateApi";
import { useDispatch } from "react-redux";
import { login, logout } from "~/action/Auth.action";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import SocialContainer from "~/components/common/SocialContainer";

function LoginPage() {
    const dispatch = useDispatch();
    const { loginApi } = authenticateApi();
    const navigate = useNavigate();
    const [searchParams, setSearchParams] = useSearchParams();

    const {
        register: registerForm,
        handleSubmit,
        formState: { errors },
    } = useForm();

    const onSubmit = (data) => {
        dispatch(showLoader());

        loginApi(data)
            .then((response) => {
                dispatch(login(response.data));
                const previousPath = localStorage.getItem("previousPath");
                if (previousPath) {
                    localStorage.removeItem("previousPath");
                    navigate(previousPath);
                } else {
                    navigate("/");
                }

                dispatch(hideLoader());
                NotificationManager.success("Đăng nhập thành công", "Thành công");
            })
            .catch(({ data }) => {
                dispatch(hideLoader());
                NotificationManager.error(data?.message, "Lỗi");
            })
            .finally(() => {
                dispatch(hideLoader());
            });
    };

    useEffect(() => {
        document.title = "Đăng nhập";
        const refreshToken = searchParams.get("refreshToken");

        if (refreshToken) {
            localStorage.setItem("accessToken", refreshToken);
            window.location.href = "/";
        } else {
            localStorage.removeItem("accessToken");
            dispatch(logout());
        }
    }, []);

    return (
        <div className="relative">
            <div className="w-100 max-w-[900px] mx-auto">
                <Wrapper>
                    <div className="row mx-0">
                        <div className="col-md-7 mt-3 px-10 py-4">
                            <div className="text-2xl font-bold mb-3">
                                <h2 className="text-center">Đăng nhập tài khoản</h2>
                            </div>
                            <form onSubmit={handleSubmit(onSubmit)} noValidate>
                                <Input
                                    className="mb-3"
                                    leftIcon={<HiOutlineUser />}
                                    label="Username"
                                    name="username"
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

                                <div className="w-100 text-right text-dark-900 text-sm mb-3 hover:text-dark-600">
                                    <Link to="/forgot-password">Quên mật khẩu ?</Link>
                                </div>
                                <Button className="mb-3 rounded-lg w-100">Login</Button>
                            </form>
                            <div className="text-center text-dark-700">
                                <span>
                                    Bạn chưa có tài khoảng
                                    <Link className="text-blue-500 font-bold ml-1" to={`/register`}>
                                        Đăng ký ngay !
                                    </Link>
                                </span>
                            </div>

                            <SocialContainer />
                        </div>
                        <div className="col-md-5 flex p-0 bg-blue-200 align-items-center">
                            <img src={poster11} alt="" />
                        </div>
                    </div>
                </Wrapper>
            </div>
        </div>
    );
}

export default LoginPage;
