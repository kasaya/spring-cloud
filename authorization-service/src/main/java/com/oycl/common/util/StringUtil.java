package com.oycl.common.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;

public class StringUtil extends StringUtils {


    /**
     * 空
     */
    public static final String EMPTY = "";

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean isEmpty(String s) {
        if (null == s || "".equals(s) || "null".equals(s.toLowerCase())) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean isBlank(String s) {
        if (null == s || "".equals(s) || "".equals(s.trim())
                || "null".equals(s.toLowerCase())) {
            return true;
        }
        return false;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    public static String trim(String s) {
        if (null == s) {
            return null;
        } else {
            return s.trim();
        }
    }

}
