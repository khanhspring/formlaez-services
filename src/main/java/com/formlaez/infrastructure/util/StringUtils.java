package com.formlaez.infrastructure.util;

import org.apache.commons.lang3.RandomStringUtils;

public class StringUtils {

    public static String randomCode() {
        return randomCode(15);
    }

    public static String randomCode(int length) {
        return RandomStringUtils.randomAlphanumeric(length).toLowerCase();
    }
}
