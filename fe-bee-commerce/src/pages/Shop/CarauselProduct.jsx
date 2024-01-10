import React, { useRef, useState } from "react";
import AliceCarousel from "react-alice-carousel";
import "react-alice-carousel/lib/alice-carousel.css";
import demo from "../../Images/cbe103a223c85d4007741451058e886d.jpg";
import { MdOutlineNavigateNext } from "react-icons/md";
import { GrFormPrevious } from "react-icons/gr";
import "./Shop.css";
import ProductSale from "~/components/User/ProductSale/ProductSale";

const Carausel = ({ mockProducts }) => {
    const responsive = {
        0: { items: 1 },
        720: { items: 2 },
        1024: { items: 6.7 },
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

    const items = mockProducts.map((item, index) => (
        <div className="w-[170px]">
            <ProductSale key={index} {...item} />
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
                        top: "8rem",
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
                            top: "8rem",
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

export default Carausel;
