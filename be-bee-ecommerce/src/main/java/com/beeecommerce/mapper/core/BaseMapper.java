package com.beeecommerce.mapper.core;

import java.util.function.Function;

public interface BaseMapper<T, R> extends Function<T, R> {
    default T reverse(R r) {
        throw new UnsupportedOperationException("Override this method to use");
    }
}
