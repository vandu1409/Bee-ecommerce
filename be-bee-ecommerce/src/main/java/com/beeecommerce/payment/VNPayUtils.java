package com.beeecommerce.payment;

import com.beeecommerce.constance.TransactionType;
import com.beeecommerce.constance.VNPayParameter;
import com.beeecommerce.entity.Account;
import com.beeecommerce.entity.Vendor;
import com.beeecommerce.exception.common.VNPayPaymentException;
import com.beeecommerce.util.ObjectHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.beeecommerce.constance.VNPayParameter.*;


public class VNPayUtils {

    @Setter
    @Getter
    private String payUrl; // Prefix url

    @Setter
    @Getter
    private String vnp_ReturnUrl;

    @Setter
    @Getter
    private String vnp_apiUrl;

    @Setter
    @Getter
    private int expiresInMinutes = 15;

    private final String SECURE_HASH = "vnp_SecureHash";

    @Setter
    private String datetimePattern = "yyyyMMddHHmmss";

    private final Map<String, String> params = new HashMap<>();

    @Setter
    private String vnpHashSecretKey;

    public VNPayUtils(String vnpHashSecretKey, String TmnCode) {
        initDefaultValue(TmnCode);
        this.vnpHashSecretKey = vnpHashSecretKey;
    }

    private void initDefaultValue(String TmnCode) {
        setParameter(LOCALE, "vn");
        setParameter(VERSION, "2.0.0");
        setParameter(COMMAND, "pay");
        setParameter(CURR_CODE, "VND");
        setParameter(TMN_CODE, TmnCode);
    }


    public void setHashSecretKey(String key) {
        this.vnpHashSecretKey = key;
    }


    public void setAmount(long amount) {
        setParameter(AMOUNT, String.valueOf(amount));
    }


    public void setIpAddress(@NonNull HttpServletRequest request) {
        String ipAddr = request.getHeader("X-FORWARDED-FOR");
        if (ipAddr == null) {
            ipAddr = request.getRemoteAddr();
        }
        setParameter(IP_ADDR, ipAddr);
    }


    public void setReturnUrl(String url) {
        setParameter(RETURN_URL, url);
    }


    public void setOrderInfo(String orderInfo) {
        setParameter(ORDER_INFO, orderInfo);
    }


    public void setOrderType(String orderType) {
        setParameter(ORDER_TYPE, orderType);
    }


    public void setTxnRef(String txnRef) {
        setParameter(TXN_REF, txnRef);
    }

    public void setCreateTime(String createTime) {
        setParameter(CREATE_DATE, createTime);
    }

    public void setExpireTime(String expireTime) {
        setParameter(EXPIRE_DATE, expireTime);
    }

    public void initTime(int duration) {
        ObjectHelper.setIfNotNull(getDateTimeFromNow(0), this::setCreateTime);
        ObjectHelper.setIfNotNull(getDateTimeFromNow(duration), this::setExpireTime);
    }

    public VNPayUtils validate() throws VNPayPaymentException {
        // Validate required params
        List<String> missParam = new ArrayList<>();

        for (VNPayParameter param : VNPayParameter.values()) {
            if (param.isRequired() && !params.containsKey(param.getValue())) {
                missParam.add(param.name());
            }
        }

        if (!missParam.isEmpty()) {
            throw new VNPayPaymentException("Missing required params: " + Arrays.toString(missParam.toArray()));
        }

        // Validate hash secret key
        if (vnpHashSecretKey.isEmpty()) {
            throw new VNPayPaymentException("Missing hash secret key");
        }

        //Validate TmnCode
        if (params.get(TMN_CODE.getValue()).isEmpty()) {
            throw new VNPayPaymentException("Missing TmnCode");
        }

        return this;
    }


    public void setParameter(@NonNull VNPayParameter param, String value) {
        params.put(param.getValue(), value);
    }

    public void setParameters(@NonNull Map<VNPayParameter, String> map) {
        for (Map.Entry<VNPayParameter, String> entry : map.entrySet()) {
            setParameter(entry.getKey(), entry.getValue());
        }
    }



    private String toParameterUri() {

        Set<String> keys = params.keySet();

        Iterator<String> iterator = keys.iterator();

        StringBuilder sb = new StringBuilder();

        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = params.get(key);

            sb
                    .append(URLEncoder.encode(key, StandardCharsets.US_ASCII))
                    .append("=")
                    .append(URLEncoder.encode(value, StandardCharsets.US_ASCII));

            if (iterator.hasNext()) {
                sb.append("&");
            }

        }

        return sb.toString();

    }

    private String getDateTimeFromNow(int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minutes);
        SimpleDateFormat sdf = new SimpleDateFormat(datetimePattern);
        return sdf.format(cal.getTime());
    }

    // Check if param is set

    public boolean isSetParameter(@NonNull VNPayParameter param) {
        return !params.containsKey(param.getValue()) || params.get(param.getValue()).isEmpty();
    }


    public String generateUrl() throws NoSuchAlgorithmException, InvalidKeyException {

        if (isSetParameter(CREATE_DATE)) {
            setParameter(CREATE_DATE, getDateTimeFromNow(0));
        }

        if (isSetParameter(EXPIRE_DATE)) {
            setParameter(EXPIRE_DATE, getDateTimeFromNow(expiresInMinutes));
        }

        //Validate required params and hash secret key
        validate();

        //Set time for hash
        String secureHash = VNPayConfig.hashAllFields(params, vnpHashSecretKey);

        params.put(SECURE_HASH, secureHash);

        return payUrl + "?" + toParameterUri();
    }

    public static VNPayPaymentRequest getRequestDeposit(Long amount, Account account) {
        return new VNPayPaymentRequest() {
            @Override
            public Long getAmount() {
                return amount;
            }

            @Override
            public String getOrderInfo() {
                return "Nap tien cho tai khoang " + account.getUsername();
            }

            @Override
            public String getOrderType() {
                return TransactionType.DEPOSIT.name();
            }

            @Override
            public String getTxnRef() {
                return TransactionType.DEPOSIT.name() + "_" + account.getId();
            }

            @Override
            public boolean readyToPay() {
                return true;
            }
        };
    }

    public static boolean checksum(Map<String, String> fields, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        @NonNull String vnp_SecureHash = fields.get("vnp_SecureHash");

        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");

        // Map value using URLDecoder
        fields.replaceAll((k, v) -> URLEncoder.encode(v, StandardCharsets.US_ASCII));

        String signValue = VNPayConfig.hashAllFields(fields, secretKey);
        return vnp_SecureHash.equals(signValue);
    }



}
