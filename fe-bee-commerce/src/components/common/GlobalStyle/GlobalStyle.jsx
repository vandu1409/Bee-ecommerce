import "./style/GlobalStyle.scss";
import 'react-notifications/lib/notifications.css';
import 'boxicons/css/boxicons.min.css';
import './style/CustomizeNotification.css';



function GlobalStyle({ children }) {
    return (
        <>
            {children}
        </>
    );
}

export default GlobalStyle;
