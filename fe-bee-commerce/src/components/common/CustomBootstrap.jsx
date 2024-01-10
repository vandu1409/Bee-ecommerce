import {ThemeProvider} from "react-bootstrap";
import config from "~/components/common/GlobalStyle/config/config.json";
function CustomBootstrap({children}) {
    return (
        <ThemeProvider prefixes={config}>{children}</ThemeProvider>
    );
}

export default CustomBootstrap;