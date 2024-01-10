import google from "~/Images/google.png";
function SocialContainer() {
    return (
        <>
            <div className="relative flex justify-content-center">
                <hr className="w-64 h-px my-4 bg-dark-800 border-0"/>
                <span
                    className="absolute px-2 text-gray-900 -translate-x-1/2 bg-white left-1/2 translate-y-1/2 top-0">or continue with</span>
            </div>
            <div className="flex justify-content-center">
                <a href={process.env.REACT_APP_GG_LOGIN_URL}>
                    <img className="w-[30px] h-[30px]" src={google} alt=""/>
                </a>
            </div>
        </>
    );
}

export default SocialContainer;
