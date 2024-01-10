function Wrapper({ className = "", children }) {
    return <div className={`bg-tw-white  shadow-border rounded-md ${className}`}>{children}</div>;
}

export default Wrapper;
