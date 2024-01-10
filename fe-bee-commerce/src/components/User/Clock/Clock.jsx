import React from "react";
import useCountDown from "../../../hooks/useCountDown";

const Clock = ({ target }) => {
    const targetDate = new Date(target).getTime();

    const { days, hours, minutes, seconds } = useCountDown(targetDate);

    const clockStyle = "bg-red-500 rounded-md mx-2 px-2";

    return (
        <div className="clock-wrapper d-flex align-items-center" style={{ marginLeft: "10px" }}>
            <div className={clockStyle}>
                <span className="text-white text-center ">{days}</span>
                <span className="text-white ml-1">Day</span>
            </div>
            <div className="text-[22px] font-bold">:</div>
            <div className={clockStyle}>
                <span className="text-white text-center">{hours}</span>
                <span className="text-white ml-1">Hour</span>
            </div>
            <div className="text-[22px] font-bold">:</div>
            <div className={clockStyle}>
                <span className="text-white text-center">{minutes}</span>
                <span className="text-white ml-1">Min</span>
            </div>
            <div className="text-[22px] font-bold">:</div>
            <div className={clockStyle}>
                <span className="text-white text-center">{seconds}</span>
                <span className="text-white ml-1">Sec</span>
            </div>
        </div>
    );
};

export default Clock;
