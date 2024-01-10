import React, { useRef, useState } from "react";
import AliceCarousel from "react-alice-carousel";
import "react-alice-carousel/lib/alice-carousel.css";

import { MdOutlineNavigateNext } from "react-icons/md";
import { GrFormPrevious } from "react-icons/gr";
import VoucherShop from "./VoucherShop";

const CarauselVoucher = () => {
    const responsive = {
        0: { items: 1 },
        720: { items: 2 },
        1024: { items: 3.4 },
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

    const items = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1].map((item, index) => (
        <div className="mr-[300px]">
            <VoucherShop key={index} />
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
                        top: "3rem",
                        right: "-2rem",
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
                            top: "3rem",
                            left: "-2rem",
                        }}
                    >
                        <GrFormPrevious />
                    </button>
                )}
            </div>
        </div>
    );
};

export default CarauselVoucher;
