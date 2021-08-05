package com.opitzconsulting.fluentdomainfactorydemo.util;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public final class BigDecimalUtil {

    private BigDecimalUtil() {
        //prevent instantiation
    }

    public static BigDecimal valueOrZero(BigDecimal fieldValue) {
        return defaultIfNull(fieldValue, zero()).setScale(2, GlobalConstants.ROUNDING_MODE);
    }


    public static String format(BigDecimal value) {

        final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
        numberFormat.setGroupingUsed(true);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(value);
    }

    public static BigDecimal zero() {
        return bigDecimalOf(0);
    }

    public static BigDecimal bigDecimalOf(int value) {
        return bigDecimalOf((double) value);
    }

    public static BigDecimal bigDecimalOf(double value) {
        return BigDecimal.valueOf(value).setScale(2, GlobalConstants.ROUNDING_MODE);
    }

    public static BigDecimal bigDecimalOfDouble(double value) {
        return bigDecimalOf(value);
    }

    public static BigDecimal bigDecimalOfLong(long value) {
        return BigDecimal.valueOf(value).setScale(2, GlobalConstants.ROUNDING_MODE);
    }

}
