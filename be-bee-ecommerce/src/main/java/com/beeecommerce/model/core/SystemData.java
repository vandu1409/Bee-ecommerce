package com.beeecommerce.model.core;

import com.beeecommerce.entity.MetaData;

import java.util.ArrayList;
import java.util.Collection;

public class SystemData extends ArrayList<MetaData> {
    public SystemData(Collection<? extends MetaData> metaData) {
        super(metaData);
    }

    public String getValue(String key) {
        MetaData metaData = this.stream()
                .filter(m -> m.getKey().equals(key))
                .findFirst()
                .orElse(null);
        if (metaData == null) {
            return null;
        }
        return metaData.getValue();
    }

    public Integer getIntValue(String key) {
        return Integer.parseInt(getValue(key));
    }

    public Long getLongValue(String key) {
        return Long.parseLong(getValue(key));
    }

    public Double getDoubleValue(String key) {
        return Double.parseDouble(getValue(key));
    }

    public Boolean getBooleanValue(String key) {
        return Boolean.parseBoolean(getValue(key));
    }

    public String getDescription(String key) {
        MetaData metaData = this.stream()
                .filter(m -> m.getKey().equals(key))
                .findFirst()
                .orElse(null);
        if (metaData == null) {
            return null;
        }
        return metaData.getDescription();
    }
}
