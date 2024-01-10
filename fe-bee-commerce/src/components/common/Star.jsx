import {BsStar, BsStarFill} from "react-icons/bs";

function Star({ point = 0.0, className }) {
    const roundPoint = point.toFixed(1);

    const getPercent = (star) => {
        if (roundPoint >= star) return 100;
        if (roundPoint < star - 1) return 0;
        return Math.round((roundPoint - star + 1) * 100);
    };

    return (
        <div>
            <div className={`flex text-mini text-yellow-400 ${className}`}>
                <span className="relative">
                    <BsStar />
                    <div className={`w-[${getPercent(1)}%] overflow-hidden absolute top-0`}>
                        <BsStarFill className="" />
                    </div>
                </span>
                <span className="relative">
                    <BsStar />
                    <div className={`w-[${getPercent(2)}%] overflow-hidden absolute top-0`}>
                        <BsStarFill className="" />
                    </div>
                </span>
                <span className="relative">
                    <BsStar />
                    <div className={`w-[${getPercent(3)}%] overflow-hidden absolute top-0`}>
                        <BsStarFill className="" />
                    </div>
                </span>
                <span className="relative">
                    <BsStar />
                    <div className={`w-[${getPercent(4)}%] overflow-hidden absolute top-0`}>
                        <BsStarFill className="" />
                    </div>
                </span>
                <span className="relative">
                    <BsStar />
                    <div className={`w-[${getPercent(5)}%] overflow-hidden absolute top-0`}>
                        <BsStarFill className="" />
                    </div>
                </span>
            </div>
        </div>
    );
}

export default Star;
