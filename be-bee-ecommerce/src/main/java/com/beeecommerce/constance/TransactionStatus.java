package com.beeecommerce.constance;

public enum TransactionStatus {
    SUCCESS("S"),
    PENDING("P"),
    FAILED("F");

    private final String value;

    TransactionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
