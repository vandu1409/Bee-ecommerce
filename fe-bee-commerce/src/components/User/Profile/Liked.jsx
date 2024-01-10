import { useSelector } from "react-redux";
import { userDataSelector } from "~/selector/Auth.selector";
import ProductTray from "../Flash_Sale/ProductTray";
import WishlistApi from "~/api/wishlistApi";
import CustomPagination from "~/components/common/CustomPagination";
import { useEffect, useState } from "react";

const Liked = () => {

      const [products, setProducts] = useState([]);
      //pagination 
      const [page, setPage] = useState({currentPage: 0, totalPages: 0});
      const {getWishlist} = WishlistApi();

      useEffect(() => {
           getWishlist(page.currentPage)
           .then(response => {
               setProducts(response.data);
               setPage(response.pagination)
           })
      }, []);


    return <>
    <ProductTray title="Danh yêu thích" products={products}/>
     <CustomPagination {...page}/>
    </>;
};

export default Liked;
