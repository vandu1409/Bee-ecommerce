package com.beeecommerce.payment;

import com.beeecommerce.constance.TransactionType;
import com.beeecommerce.exception.common.ParamInvalidException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Getter
@Slf4j
public class TxnRef {
    final Long id;

    final TransactionType type;

    final String extra;

    public TxnRef(Long id, TransactionType type) {
        this.id = id;
        this.type = type;
        this.extra = "";
    }

    public TxnRef(Long id, TransactionType type, String extra) {
        this.id = id;
        this.type = type;
        if (extra != null && !extra.contains("_")) {
            this.extra = extra;
        } else {
            log.warn("Invalid extra: {} auto set random extra ", extra);
            this.extra = UUID.randomUUID().toString();
        }
    }

    @Override
    public String toString() {
        return type.name() + "_" + id + "_" + extra;
    }

    public static TxnRef fromString(String str) {
        try {
            String[] parts = str.split("_");
            return new TxnRef(Long.parseLong(parts[1]), TransactionType.valueOf(parts[0]), parts[2]);
        } catch (Exception e) {
            return null;
        }
    }
}
