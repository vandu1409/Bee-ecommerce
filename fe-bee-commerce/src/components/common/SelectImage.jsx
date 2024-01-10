import { LuImagePlus } from "react-icons/lu";
import * as PropTypes from "prop-types";
import { useEffect, useMemo, useState } from "react";

function SelectImage({ onChange, disabled, title, replace, inputRef, imgClassName, imageUrl, isReset }) {
    const id = useMemo(() => {
        return Math.random().toString(36).substring(2);
    }, []);

    const [selectImage, setSelectImage] = useState("");

    function handleChange(e) {
        if (onChange && typeof onChange === "function") {
            onChange(e);
        }

        if (replace && e.target.files[0]) {
            const url = URL.createObjectURL(e.target.files[0]);
            setSelectImage(url);
        }
    }

    useEffect(() => {
        if (isReset) {
            setSelectImage("");
        }
    }, [isReset]);

    // Sử dụng imageUrl để hiển thị ảnh đã chọn
    const imageSource = selectImage ? selectImage : imageUrl;

    return (
        <div className="">
            {replace && imageSource ? (
                <label className="cursor-pointer" htmlFor={id}>
                    <img className={imgClassName} src={imageSource} alt="" />
                </label>
            ) : (
                <label
                    className="cursor-pointer min-w-[80px] min-h-[80px] border-1 border-dashed rounded-xl flex-col py-2 px-[3px] flex justify-center align-items-center"
                    htmlFor={id}
                >
                    <LuImagePlus className="text-2xl" />
                    <span className="text-mini text-center text-wrap inline-block w-100">{title}</span>
                </label>
            )}

            <input
                {...inputRef}
                onChange={handleChange}
                id={id}
                className="collapse"
                type="file"
                name="myImage"
                accept="image/png, image/gif, image/jpeg"
            />
        </div>
    );
}

SelectImage.propTypes = {
    images: PropTypes.arrayOf(PropTypes.any),
    onChange: PropTypes.func,
    imageUrl: PropTypes.string, // Prop imageUrl
};

export default SelectImage;
