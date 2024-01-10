package com.beeecommerce.constance;

public class ApiPaths {

    private static final String API_VERSION = "/v1";

    private static final String PREFIX_API = "/api";

    private static final String BASE_API = PREFIX_API + API_VERSION;


    public static class AuthenticatePath {

        public static final String AUTH_PREFIX = BASE_API + "/auth";

        public static final String REGISTER = "/register";

        public static final String VALIDATE = "/validate";

        public static final String LOGIN = "/login";

        public static final String LOGOUT = "/logout";

        public static final String REFRESH_TOKEN = "/refresh";

        public static final String REQUEST_RESET_PASSWORD = "/forgot-password";

        public static final String RESET_PASSWORD = "/reset-password";

        public static final String CONFIRM_ACCOUNT = "/confirm-account";

        public static final String UPDATE_ACCOUNT = "/update";

    }

    public static class UploadPath {
        public static final String UPLOAD_LINK = "/upload";
    }

    public static class CategoryPath {
        public static final String CATEGORY_PREFIX = BASE_API + "/categories";

        public static final String CATEGORY_LEVEL = "/level/{level}";

        public static final String CATEGORY_CHILDREN = "/children/{id}";
    }


    public static class StaticPagePath {
        public static final String STATIC_PAGE_PREFIX = BASE_API + "/static-page";
    }

    public static class VendorPath {
        public static final String VENDOR_PREFIX = BASE_API + "/vendors";
    }

    public static class CheckoutPath {
        public static final String CHECKOUT_PREFIX = BASE_API + "/checkout";
    }

    public static class ProductPath {
        public static final String PRODUCT_PREFIX = BASE_API + "/products";

        public static final String PRODUCT_DETAIL = "/detail";
    }

    public static class ProductTagPath {
        public static final String PRODUCT_TAG_PREFIX = BASE_API + "/product-tags";
    }

    public static class BrandPath {
        public static final String BRAND_PREFIX = BASE_API + "/brands";
    }

    public static class AttributePath {
        public static final String ATTRIBUTE_PREFIX = BASE_API + "/attributes";

        public static final String ATTRIBUTE_REQUIRE = "/require/{categoryId}";

        public static final String SEARCH_ATTRIBUTE = "/search";

    }

    public static class AddressPath {
        public static final String ADDRESS_PREFIX = BASE_API + "/addresses";

        public static final String ADDRESS_PROVINCE = "/provinces";

        public static final String ADDRESS_DISTRICT = "/districts/{provinceId}";

        public static final String ADDRESS_WARD = "/wards/{districtId}";

        public static final String WARD_DETAIL = "/wards/detail/{wardId}";
    }

    public static class WishlistPath {
        public static final String WISHLIST_PREFIX = BASE_API + "/wishlists";
    }

    public static class VoucherPath {
        public static final String VOUCHER_PREFIX = BASE_API + "/vouchers";
    }

    public static class HashtagPath {
        public static final String HASHTAG_PREFIX = BASE_API + "/hashtags";
    }

    public static class CartPath {
        public static final String CART_PREFIX = BASE_API + "/carts";
    }

    public static class StatisticalPath {
        public static final String STATISTICAL_PREFIX = BASE_API + "/statistical";
    }

    public static class PaymentPath {
        public static final String PAYMENT_PREFIX = BASE_API + "/payment";

        public static final String PAY_ORDER_VNPAY = "/order/vnpay/{orderId}";

        public static final String REQUEST_DEPOSIT = "/request-deposit";

        public static final String PROCESS = "/process";
    }

    public static class FeedbackPath {
        public static final String FEEDBACK_PREFIX = BASE_API + "/feedback";
    }

    public static class SearchPath{
        public static final String SEARCH_SUFFIX = BASE_API+"/search";
    }

    public static class GHNPath {
        public static final String GHN_PREFIX = BASE_API + "/GHN";
    }
}
