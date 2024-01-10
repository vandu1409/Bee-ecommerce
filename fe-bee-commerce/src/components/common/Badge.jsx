function Badge({ icon, className, children, type = "primary", ...passProps }) {
    switch (type) {
        case "success":
            return (
                <div
                    {...passProps}
                    className={`inline-flex items-center rounded-full bg-green-50 px-2 py-1 text-xs font-medium text-green-600 ring-1 ring-inset ring-green-500/40 ${className}`}
                >
                    {icon && <span className="mr-1">{icon}</span>}
                    {children && <span className="whitespace-nowrap">{children}</span>}
                </div>
            );
        case "warning":
            return (
                <div
                    {...passProps}
                    className={`inline-flex items-center rounded-full bg-yellow-50 px-2 py-1 text-xs font-medium text-yellow-600 ring-1 ring-inset ring-yellow-500/40 ${className}`}
                >
                    {icon && <span className="mr-1">{icon}</span>}
                    {children && <span>{children}</span>}
                </div>
            );
        case "danger":
            return (
                <div
                    {...passProps}
                    className={`inline-flex items-center rounded-full bg-red-50 px-2 py-1 text-xs font-medium text-red-600 ring-1 ring-inset ring-red-500/40 ${className}`}
                >
                    {icon && <span className="mr-1">{icon}</span>}
                    {children && <span>{children}</span>}
                </div>
            );
        default:
            return (
                <div
                    {...passProps}
                    className={`inline-flex items-center rounded-full bg-blue-50 px-2 py-1 text-xs font-medium text-blue-600 ring-1 ring-inset ring-blue-500/40${className}`}
                >
                    {icon && <span className="mr-1">{icon}</span>}
                    {children && <span>{children}</span>}
                </div>
            );
    }
}

export default Badge;
