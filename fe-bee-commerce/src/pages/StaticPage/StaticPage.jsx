import Wrapper from "~/components/common/Wrapper";
import {useEffect, useRef} from "react";
import staticPageApi from "~/api/staticPageApi";
import {useSearchParams} from "react-router-dom";
import {NotificationManager} from "react-notifications";

function StaticPage() {

    const {getStaticPageByPath} = staticPageApi();
    const container = useRef();
    const [searchParam, setSearchParam] = useSearchParams();

    useEffect(() => {
        const path = searchParam.get("p");
        if (!path) {
            NotificationManager.error("Trang không hợp lệ", "Lỗi", 99999);
            return;
        };
        getStaticPageByPath(path)
            .then(({data}) => {
                container.current.innerHTML = data.content;
                document.title = data.title;
            })
            .catch(({data}) => {
                NotificationManager.error(data.message, "Lỗi");
            });
    }, []);

    return (
        <Wrapper className="px-[25px] py-[30px] ql-snow">
            <div className="ql-editor">
                <div ref={container}></div>
            </div>
        </Wrapper>
    );
}

export default StaticPage;