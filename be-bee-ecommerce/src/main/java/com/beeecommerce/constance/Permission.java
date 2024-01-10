package com.beeecommerce.constance;

import lombok.Getter;

@Getter
public enum Permission {

    ADD_PRODUCT,
    LOCK_ACCOUNT,
    CREATE_ORDER,
    DELETE_PRODUCT;

    private String permission;

}
