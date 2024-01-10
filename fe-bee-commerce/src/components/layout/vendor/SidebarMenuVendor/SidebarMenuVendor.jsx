import {useState} from "react";
import {BsBox2, BsChat, BsDot} from "react-icons/bs";
import {IoChevronDownOutline} from "react-icons/io5";
import {Link} from "react-router-dom";
import "./SidebarMenuVendor.scss"

function SidebarMenuVendor({ data = [] }) {
    const [dataRender, setDataRender] = useState(data);

    const handleToggle = (index) => {
        dataRender[index].isOpen = !dataRender[index].isOpen;
        setDataRender([...dataRender]);
    };

    return (
        <div className="bg-blue-50 z-50 py-2 px-2 h-100 shadow overflow-y-scroll SidebarMenuVendor">
            {dataRender &&
                dataRender.map(({ icon: Icon, name, sub, isOpen }, index) => (
                    <div className="py-1" key={index} onClick={() => handleToggle(index)}>
                        {/* Parent menu */}
                        <div className="flex align-items-center px-2 text-dark-900 py-2 cursor-pointer  ">
                            <span className="mr-2">{Icon ? <Icon /> : <BsDot />}</span>
                            <span className="flex-1 text-md font-semibold">{name}</span>
                            <span
                                className={`text-sm transition-transform duration-300 ${
                                    isOpen && "rotate-180"
                                }`}
                            >
                                <IoChevronDownOutline />
                            </span>
                        </div>
                        {/* Children Menu */}
                        <div className={`${isOpen ? "" : "collapse"}`}>
                            {sub &&
                                sub.map(({ name, link }, index) => (
                                    <Link to={link}>
                                        <div className="flex py-2 align-items-center px-3 text-dark-900 transition-all duration-200 hover:bg-blue-100 hover:rounded-lg">
                                            <span className="mr-2 invisible">
                                                <BsDot />
                                            </span>
                                            <span className="flex-1 text-md text-sm">{name}</span>
                                        </div>
                                    </Link>
                                ))}
                        </div>
                    </div>
                ))}
        </div>
    );
}

export default SidebarMenuVendor;
