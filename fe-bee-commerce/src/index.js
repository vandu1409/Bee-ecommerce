import React from "react";
import { Provider } from "react-redux";
import ReactDOM from "react-dom/client";
import { NotificationContainer } from "react-notifications";
import App from "./App";
import store from "./store";
import GlobalStyle from "./components/common/GlobalStyle/GlobalStyle";
import PropagateLoader from "~/loading/PropagateLoader";
import { ThemeProvider } from "react-bootstrap";
import * as PropTypes from "prop-types";
import CustomBootstrap from "~/components/common/CustomBootstrap";

const root = ReactDOM.createRoot(document.getElementById("root"));


root.render(
    <Provider store={store}>
        <GlobalStyle>
            <CustomBootstrap>
                <App />
            </CustomBootstrap>
        </GlobalStyle>
    </Provider>,
);
