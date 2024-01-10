import {useState} from "react";
import {BsArrowBarRight, BsArrowLeft, BsDot, BsLaptop, BsPhone, BsWallet,} from "react-icons/bs";
import {Link} from "react-router-dom";
import Wrapper from "./Wrapper";

const mockData = [
    {
        name: "Điện thoại",
        icon: BsPhone,
        link: "#",
    },
    {
        name: "Laptop",
        icon: BsLaptop,
        link: "#",
    },
    {
        name: "Điện thoại",
        link: "#",
    },
    {
        name: "Laptop",
        link: "#",
    },
    {
        name: "Other",
        icon: BsWallet,
        children: [
            {
                name: "Điện thoại",
                link: "#",
            },
            {
                name: "Laptop",
                link: "#",
            },
            {
                name: "Điện thoại",
                link: "#",
            },
            {
                name: "Laptop",
                children: [
                    {
                        name: "Điện thoại",
                        link: "#",
                    },
                    {
                        name: "Điện thoại",
                        link: "#",
                    },
                ],
            },
        ],
    },
];

function PopupMenu({ data = mockData, header = "" }) {
    const [indexMenu, setIndexMenu] = useState(null);
    const [renderData, setRenderData] = useState(data);
    const [renderHeader, setRenderHeader] = useState(header);


    const getItemProps = ({ name, children, link = "#" }, index) => {
        if (!children) return { to: link };

        return {
            onClick: () => {
                setRenderData(children);
                setRenderHeader(name);
                if (indexMenu == null) {
                    setIndexMenu([index]);
                } else {
                    setIndexMenu([...indexMenu, index]);
                }
            },
        };
    };

    const handlerBack = () => {
        if (!indexMenu) return;

        if (indexMenu.length === 1) {
            setIndexMenu(null);
            setRenderData(data);
            setRenderHeader(header);
            return;
        } else {
            const temp = [...indexMenu];
            //[1, 3, 4];
            temp.pop();
            setIndexMenu(temp);
            //[1, 3];

            const subMenuIndex = temp[temp.length - 1];
            let subMenu = data;
            let tempHeader = "Home";
            for (let i in temp) {
                subMenu = subMenu[temp[i]].children;
                header = subMenu.name;
            }
            setRenderData(subMenu);
            setRenderHeader(tempHeader);
        }
    };

    return (
        <Wrapper className="rounded-xl font-semibold ">
            {renderHeader && (
                <header className="relative px-3 py-2 border-b bg--200">
                    {renderData != data && (
                        <span
                            onClick={handlerBack}
                            className="text-[20px] absolute py-1 px-1 cursor-pointer transition-transform duration-300 hover:-translate-x-[2px] "
                        >
                            <BsArrowLeft />
                        </span>
                    )}
                    <h1 className="px-[35px] py-1">{renderHeader}</h1>
                </header>
            )}
            <main className="pb-2 ">
                {renderData.map((item, index) => {
                    const { name, icon: Icon = BsDot, children, link } = item;
                    return (
                        <Link key={index} {...getItemProps(item, index)}>
                            <div className="flex align-items-center py-2 px-3 hover:bg-dark-50 cursor-pointer">
                                <span className="text-xl mr-[8px]">
                                    <Icon />
                                </span>
                                <span className="flex-1 pl-2">{name}</span>
                                {(
                                    <span className={`text-xl ml-[8px] ${children ? '' : 'opacity-0'}`}>
                                        <BsArrowBarRight />
                                    </span>
                                )}
                            </div>
                        </Link>
                    );
                })}
            </main>
        </Wrapper>
    );
}

export default PopupMenu;
