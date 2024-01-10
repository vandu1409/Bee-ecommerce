import React, { useRef, useState } from "react";
import AliceCarousel from "react-alice-carousel";
import "react-alice-carousel/lib/alice-carousel.css";
import { MdOutlineNavigateNext } from "react-icons/md";
import { GrFormPrevious } from "react-icons/gr";

const CarauselImage = ({ images, currentUpImg, setCurrentUpImg }) => {
    let responsive = {
        1200: { items: 4.3 }, // Điều chỉnh số items cho màn hình có độ rộng 1200
    };

    const carouselRef = useRef(null);

    const handlePrev = () => {
        if (carouselRef.current) {
            carouselRef.current.slidePrev();
        }
    };

    const handleNext = () => {
        if (carouselRef.current) {
            carouselRef.current.slideNext();
        }
    };

    const items =
        images &&
        images.map((imgUrl, index) => (
            <div
                key={index}
                className="w-[110px] h-[110px] mb-[10px]   transition-all duration-250 hover:-translate-y-[3px]"
            >
                <img
                    src={imgUrl}
                    alt=""
                    onClick={() => setCurrentUpImg(imgUrl)}
                    className={`w-full h-full border-solid rounded-lg border-blue-500  ${
                        currentUpImg === imgUrl ? "border-2" : ""
                    }`}
                />
            </div>
        ));

    return (
        <div className=" content">
            <div className="relative">
                <AliceCarousel
                    mouseTracking
                    items={items}
                    disableButtonsControls
                    disableDotsControls
                    responsive={responsive}
                    ref={carouselRef}
                />

                <button
                    onClick={handleNext}
                    className="z-50 bg-white p-1 text-[30px] text-[#3f4b53] bg-[#e7e8ea] border-1 border-[#e7e8ea]"
                    style={{
                        position: "absolute",
                        top: "2rem",
                        right: "0rem",
                    }}
                >
                    <MdOutlineNavigateNext />
                </button>

                {carouselRef !== null && (
                    <button
                        onClick={handlePrev}
                        className="z-50 bg-white p-1 text-[30px] text-[#3f4b53] bg-[#e7e8ea] border-1 border-[#e7e8ea]"
                        style={{
                            position: "absolute",
                            top: "2rem",
                            left: "0rem",
                        }}
                    >
                        <GrFormPrevious />
                    </button>
                )}
            </div>
        </div>
    );
};

export default CarauselImage;
