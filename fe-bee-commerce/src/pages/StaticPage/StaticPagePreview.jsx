import {useSearchParams} from "react-router-dom";
import {useEffect, useRef} from "react";
import Wrapper from "~/components/common/Wrapper";
import "quill/dist/quill.snow.css";
function StaticPagePreview({children}) {

    // useSeachQuery
    const [searchParam, setSearchParam] = useSearchParams();

    const container = useRef();

    useEffect(() => {
        const base64Data = searchParam.get("d");
        const base64Title = searchParam.get("t");
        const title = decodeURIComponent(base64Title);
        const data = decodeURIComponent(base64Data);
        if (!data) return;

        container.current.innerHTML = data;
        title && (document.title = title);
    }, []);


    return (
        <Wrapper className="px-[25px] py-[30px] ql-snow">
            <div className="ql-editor">
                <div ref={container}></div>
            </div>
        </Wrapper>
    );
}


export default StaticPagePreview;