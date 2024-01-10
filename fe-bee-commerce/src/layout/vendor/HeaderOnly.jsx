import {useState} from "react";
import HeaderVendor from "~/components/layout/vendor/HeaderVendor/HeaderVendor";

const mockBreadcrumb = [
  { name: "Home", to: "/vendor" },
  { name: "Products", to: "/vendor/products" },
  { name: "Add new product", to: "/vendor/products/add" },
];

function HeaderOnly({ children }) {
  const [breadcrumb, setBreadcrumb] = useState(mockBreadcrumb);

  return (
    <div className="vh-100 relative">
      <div className="h-[73px] ">
        <HeaderVendor breadcrumb={breadcrumb} />
      </div>
      <div className="container-fluid px-0 h-[calc(100%-73px)]">
        <div className="flex h-100">
          <div className="w-100 p-3 pt-4 overflow-y-scroll">
            <div className="container">{children}</div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default HeaderOnly;
