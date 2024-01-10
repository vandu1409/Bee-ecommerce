import {Link, useNavigate} from "react-router-dom";
import "./Login.css";
import Wrapper from "../../components/common/Wrapper";
import Input from "../../components/common/Input";
import {HiOutlineLockClosed, HiOutlinePhone, HiOutlineUser} from "react-icons/hi";
import {MdOutlineAlternateEmail} from "react-icons/md";
import {LiaIdCard} from "react-icons/lia";
import Button from "../../components/common/Button";
import poster11 from "../../Images/poster_11.png";
import authenticateApi from "~/api/authenticateApi";
import {NotificationManager} from "react-notifications";
import {useDispatch} from "react-redux";
import {hideLoader, showLoader} from "~/action/Loader.action";
import {useForm} from "react-hook-form";
import SocialContainer from "~/components/common/SocialContainer";

const RegisterPage = () => {
    const { register } = authenticateApi();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const {
        watch,
        register: registerForm,
        handleSubmit,
        formState: { errors },
    } = useForm();

    // Xu ly submit form dang ky
    const onSubmit = (data) => {
        dispatch(showLoader());
        register(data)
            .then(() => {
                navigate("/login");
                NotificationManager.success("Kiểm tra email để kích hoạt tài khoản","Đăng ký thành công");
            })
            .catch(({ data }) => {
                NotificationManager.error(data.message, "Lỗi");
            })
            .finally(() => {
                dispatch(hideLoader());
            });
    };

    return (
        <div className="w-100 max-w-[900px] mx-auto">
            <Wrapper>
                <div className="row mx-0">
                    <div className="col-md-7 mt-3 px-10 py-4">
                        <form onSubmit={handleSubmit(onSubmit)} noValidate>
                            <div className="text-2xl font-bold mb-3">
                                <h2 className="text-center">Đăng ký tài khoản</h2>
                            </div>
                            <Input
                                className="mb-3"
                                leftIcon={<HiOutlineUser />}
                                label="Username"
                                name="username"
                                errorMessage={errors.username?.message}
                                messageClass="text-red-500"
                                inputRef={registerForm("username", {
                                    required: { value: true, message: "Username is required" },
                                    minLength: {value: 6, message: "Username must be at least 6 characters"},
                                    maxLength: {value: 25, message: "Username must be at most 25 characters"},
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
                                        if (watch("password") != value) {
                                            return "Your passwords do no match";
                                        }
                                    },
                                })}
                            />
                            <Input
                                className="mb-3"
                                leftIcon={<LiaIdCard />}
                                label="FullName"
                                name="fullname"
                                messageClass="text-red-500"
                                errorMessage={errors.fullname?.message}
                                inputRef={registerForm("fullname", {
                                    required: {
                                        value: true,
                                        message: "Fullname is required",
                                    },
                                })}
                            />
                            <Input
                                className="mb-3"
                                leftIcon={<MdOutlineAlternateEmail />}
                                label="Email"
                                name="email"
                                messageClass="text-red-500"
                                errorMessage={errors.email?.message}
                                inputRef={registerForm("email", {
                                    required: {
                                        value: true,
                                        message: "Email is required",
                                    },
                                    pattern: {
                                        value: /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/,
                                        message: "Email is invalid",
                                    },
                                })}
                            />
                            <Input
                                className="mb-3"
                                leftIcon={<HiOutlinePhone />}
                                label="PhoneNumber"
                                name="phoneNumber"
                                messageClass="text-red-500"
                                errorMessage={errors.phoneNumber?.message}
                                inputRef={registerForm("phoneNumber", {
                                    required: {
                                        value: true,
                                        message: "PhoneNumber is required",
                                    },
                                    pattern: {
                                        value: /^[a-zA-Z0-9_]+$/,
                                        message: "PhoneNumber is invalid",
                                    },
                                })}
                            />

                            <div className="w-100 text-right text-dark-900 text-sm mb-3 hover:text-dark-600">
                                <Link to="/forgot-password">Quên mật khẩu?</Link>
                            </div>
                            <Button type="submit" className="mb-3 rounded-lg w-100">
                                Register
                            </Button>
                            <div className="text-center text-dark-700">
                                <p>
                                    Bạn đã có tài khoảng?
                                    <Link
                                        className="text-blue-500 font-bold ml-1"
                                        to={`/login`}
                                    >
                                        Đăng nhập ngay!
                                    </Link>
                                </p>
                            </div>
                            <SocialContainer />
                        </form>
                    </div>
                    <div className="col-md-5 flex p-0 bg-blue-200 align-items-center">
                        <img src={poster11} alt="" />
                    </div>
                </div>
            </Wrapper>
        </div>
    );
};

export default RegisterPage;
