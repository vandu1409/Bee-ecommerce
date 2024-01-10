package com.beeecommerce.util;

import com.beeecommerce.exception.common.ParamInvalidException;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ObjectHelper {

    public static boolean checkNull(Object... obj) {
        for (Object object : obj) {
            if (object == null || object.equals("")) {
                return true;
            }
        }
        return false;
    }

    public static void checkNullParam(Object... obj) throws ParamInvalidException {
        for (Object object : obj) {
            if (object == null || object.equals("")) {

                throw new ParamInvalidException("Vui lòng nhập đầy đủ thông tin!");
            }
        }
    }

    public static void checkNullParam(String message, Object... obj) throws ParamInvalidException {
        for (Object object : obj) {
            if (object == null || object.equals("")) {
                throw new ParamInvalidException(message);
            }
        }
    }

    public static <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }


    public static <T> void setIfValid(T value, Consumer<T> setter, Predicate<T> testcase) {
        if (testcase.test(value)) {
            setter.accept(value);
        }
    }


    public static <T> void setStaticPage(Supplier<T> supplier, Consumer<T> consumer) {
        T value = supplier.get();
        if (value != null) {
            consumer.accept(value);
        }
    }
}
