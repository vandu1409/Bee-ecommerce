import PropTypes from "prop-types";
import {Link} from "react-router-dom";

function Button({
    className = "",
    to,
    href,
    primary = false,
    danger = false,
    success = false,
    warning = false,
    outline = false,
    text = false,
    rounded = false,
    disabled = false,
    small = false,
    large = false,
    dark = false,
    gradient,
    children,
    onClick = () => {},
    ...passProps
}) {
    let Comp = "button";
    const props = {
        onClick,
        disabled,
        ...passProps,
    };

    // Remove event listener when btn is disabled
    if (disabled) {
        Object.keys(props).forEach((key) => {
            if (key.startsWith("on") && typeof props[key] === "function") {
                delete props[key];
            }
        });
    }

    if (to) {
        props.to = to;
        Comp = Link;
    } else if (href) {
        props.href = href;
        Comp = "a";
    }

    let colorText = "blue";
    let colorBackground = "dark";

    switch (true) {
        case primary:
            colorText = "blue";
            break;
        case danger:
            colorText = "red";

            break;
        case success:
            colorText = "green";
            break;
        case warning:
            colorText = "yellow";
            break;
        case dark:
            colorText = "dark";
        default:
            break;
    }

    if (outline) {
        className += ` border-solid border-1 bg-transparent border-${colorText}-600 text-${colorText}-600 hover:bg-${colorText}-100 `;
    } else {
        className += ` bg-${colorText}-500 text-white hover:bg-${colorText}-600`;
    }

    return (
        <Comp className={`p-2 transition-all  duration-150 ${className}`} {...props}>
            {children}
        </Comp>
    );
}

Button.propTypes = {
    className: PropTypes.string,
    to: PropTypes.string,
    href: PropTypes.string,
    primary: PropTypes.bool,
    danger: PropTypes.bool,
    success: PropTypes.bool,
    warning: PropTypes.bool,
    outline: PropTypes.bool,
    text: PropTypes.bool,
    rounded: PropTypes.bool,
    disabled: PropTypes.bool,
    small: PropTypes.bool,
    large: PropTypes.bool,
    dark: PropTypes.bool,
    gradient: PropTypes.bool,
    children: PropTypes.node.isRequired,
    onClick: PropTypes.func,
};

export default Button;
