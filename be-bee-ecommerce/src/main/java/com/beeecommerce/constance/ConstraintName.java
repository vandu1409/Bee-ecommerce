package com.beeecommerce.constance;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ConstraintName {

    /*
     *******************
     * CONSTRAINT NAME *
     *******************
     */

    public static final String ACCOUNT_USERNAME_UNIQUE = "account_username_unique";
    public static final String ACCOUNT_USERNAME_UNIQUE_MSG = "Username already exists";


    public static final String ACCOUNT_EMAIL_UNIQUE = "account_email_unique";
    public static final String ACCOUNT_EMAIL_UNIQUE_MSG = "Email already exists";


    public static final String ACCOUNT_PHONE_NUMBER_UNIQUE = "account_phone_number_unique";
    public static final String ACCOUNT_PHONE_NUMBER_UNIQUE_MSG = "Phone number already exists";


    public static final String CART_USER_ID_CLASSIFY_ID_UNIQUE = "cart_user_id_classify_id_unique";
    public static final String CART_USER_ID_CLASSIFY_ID_UNIQUE_MSG = "Cart already exists";


    public static final String CATEGORY_NAME_UNIQUE = "category_name_unique";
    public static final String CATEGORY_NAME_UNIQUE_MSG = "Category already exists";


    public static final String CATEGORY_ATTRIBUTES_CATEGORY_UNIQUE = "category_attributes_category_id_attributes_id_unique";
    public static final String CATEGORY_ATTRIBUTES_CATEGORY_UNIQUE_MSG = "Category already exists";


    public static final String CLASSIFY_GROUP_NAME_UNIQUE = "classify_group_name_unique";
    public static final String CLASSIFY_GROUP_NAME_UNIQUE_MSG = "Classify group already exists in this product";


    public static final String CLASSIFY_NAME_NAME_UNIQUE = "classify_name_name_unique";
    public static final String CLASSIFY_NAME_NAME_UNIQUE_MSG = "Classify name already exists";


    public static final String DISTRICT_NAME_UNIQUE = "district_name_unique";
    public static final String DISTRICT_NAME_UNIQUE_MSG = "District already exists in this province";


    public static final String FEEDBACK_ORDER_DETAILS_ID_UNIQUE = "feedback_order_details_id_unique";
    public static final String FEEDBACK_ORDER_DETAILS_ID_UNIQUE_MSG = "Feedback already exists in this order";


    public static final String HASHTAG_NAME_UNIQUE = "hashtag_name_unique";
    public static final String HASHTAG_NAME_UNIQUE_MSG = "Hashtag already exists try other name";


    public static final String ORDER_DETAILS_ORDER_CLASSIFY_UNIQUE = "order_details_order_id_classify_id_unique";
    public static final String ORDER_DETAILS_ORDER_CLASSIFY_UNIQUE_MSG = "Order detail already exists in this order";


    public static final String PRODUCT_ATTRIBUTES_UNIQUE = "product_attributes_unique";
    public static final String PRODUCT_ATTRIBUTES_UNIQUE_MSG = "Product attribute already exists in this product";

    public static final String PRODUCT_TAG_PRODUCT_TAG_UNIQUE = "product_tag_product_id_tag_id_unique";
    public static final String PRODUCT_TAG_PRODUCT_TAG_UNIQUE_MSG = "Product tag already exists in this product";

    public static final String VENDOR_ACCOUNT_UNIQUE = "vendor_account";
    public static final String VENDOR_ACCOUNT_UNIQUE_MSG = "This account have been registered as vendor";

    public static final String VOUCHER_CODE_UNIQUE = "voucher_code_unique";
    public static final String VOUCHER_CODE_UNIQUE_MSG = "Voucher code already exists";

    //    "wishlist_account_product"
    public static final String WISHLIST_ACCOUNT_PRODUCT_UNIQUE = "wishlist_account_product";
    public static final String WISHLIST_ACCOUNT_PRODUCT_UNIQUE_MSG = "Wishlist already exists in this product";

    /*
     **************
     *   Value    *
     **************
     */
    private static final Map<String, String> values;


    static {
        Field[] fields = ConstraintName.class.getFields();
        List<Field> fieldList = List.of(fields);

        values = new HashMap<>();

        fieldList.stream()
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> !field.getName().endsWith("_MSG"))
                .forEach(field -> {
                    try {
                        String key = (String) field.get(null);
                        String value = (String) ConstraintName.class.getField(field.getName() + "_MSG").get(null);
                        values.put(key, value);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        log.error("Error when get constraint name", e);
                    }
                });
    }


    public static Map<String, String> getConstraintName() {
        return values;
    }
}
