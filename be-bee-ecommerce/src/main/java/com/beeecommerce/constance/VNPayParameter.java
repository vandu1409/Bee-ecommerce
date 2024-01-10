package com.beeecommerce.constance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VNPayParameter {
    INV_CUSTOMER("vnp_Inv_Customer", false),
    
    INV_ADDRESS("vnp_Inv_Address", false),
    
    CURR_CODE("vnp_CurrCode", true),
    
    RETURN_URL("vnp_ReturnUrl", true),
    
    INV_COMPANY("vnp_Inv_Company", false),
    
    TMN_CODE("vnp_TmnCode", true),
    
    TXN_REF("vnp_TxnRef", true),
    
    AMOUNT("vnp_Amount", true),
    
    BILL_CITY("vnp_Bill_City", false),
    
    INV_EMAIL("vnp_Inv_Email", false),
    
    LOCALE("vnp_Locale", true),
    
    BILL_EMAIL("vnp_Bill_Email", false),
    
    BILL_MOBILE("vnp_Bill_Mobile", false),
    
    EXPIRE_DATE("vnp_ExpireDate", true),
    
    BILL_LAST_NAME("vnp_Bill_LastName", false),
    
    CREATE_DATE("vnp_CreateDate", true),
    
    VERSION("vnp_Version", true),
    
    BANK_CODE("vnp_BankCode", false),
    
    INV_TYPE("vnp_Inv_Type", false),
    
    BILL_STATE("vnp_Bill_State", false),
    
    ORDER_TYPE("vnp_OrderType", true),
    
    BILL_FIRST_NAME("vnp_Bill_FirstName", false),
    
    INV_TAX_CODE("vnp_Inv_Taxcode", false),
    
    ORDER_INFO("vnp_OrderInfo", true),
    
    IP_ADDR("vnp_IpAddr", true),
    
    COMMAND("vnp_Command", true),
    
    BILL_COUNTRY("vnp_Bill_Country", false),

    RESPONSE_CODE("vnp_ResponseCode", false),
    
    INV_PHONE("vnp_Inv_Phone", false);

    final String value;
    
    final boolean required;
}
