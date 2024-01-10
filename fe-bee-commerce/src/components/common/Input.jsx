import { useState } from "react";
import { AiOutlineEye, AiOutlineEyeInvisible } from "react-icons/ai";

function Input({
    label = "",
    leftIcon,
    rightIcon,
    type,
    textArea,
    readOnly,
    className = "",
    placeholder,
    isPassword = false,
    errorMessage = "",
    maxLength,
    messageClass = "text-red-500",
    inputRef,
    isDate = false,
    ...passProps
}) {
    const [showPassword, setShowPassword] = useState(false);

    const ComponentInput = textArea ? "textarea" : "input";
    // Handle password
    if (isPassword && !showPassword) {
        type = "password";
        rightIcon = <AiOutlineEyeInvisible />;
    } else if (isPassword && showPassword) {
        type = "text";
        rightIcon = <AiOutlineEye />;
    } else if (isDate) {
        type = "date";
    }

    const handleShow = () => {
        setShowPassword(!showPassword);
    };

    // Handle placeholder
    if (!placeholder) {
        placeholder = `Please enter ${label.toLowerCase()}`;
    }

    const iconStyle = "text-xl text-dark-700 cursor-pointer";

    return (
        <div className={`w-100 ${className}`}>
            {label && (
                <label className="mb-2 text-[14px]" htmlFor="">
                    {label}
                </label>
            )}
            <div className="w-100 flex align-items-center border-solid px-2 pe-2 border-dark-200 hover:border-dark-400 border focus:!border-5 rounded-1">
                {leftIcon && <span className={` ${iconStyle}`}>{leftIcon}</span>}
                <input
                    placeholder={placeholder}
                    type={type}
                    className="focus:outline-none flex-1 py-2 px-2 bg-transparent read-only:cursor-pointer"
                    readOnly={readOnly}
                    {...inputRef}
                    {...passProps}
                    {...textArea}
                />
                {rightIcon && (
                    <span onClick={handleShow} className={` ${iconStyle}`}>
                        {rightIcon}
                    </span>
                )}
            </div>

            {errorMessage && <span className={`text-mini ${messageClass}`}>{errorMessage}</span>}
        </div>
    );
}

export default Input;
