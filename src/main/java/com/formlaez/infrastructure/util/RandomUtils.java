package com.formlaez.infrastructure.util;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

public class RandomUtils {

    public static final char[] ALPHABET =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String randomNanoId(int length) {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, ALPHABET, length);
    }
    public static String randomNanoId() {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, ALPHABET, NanoIdUtils.DEFAULT_SIZE);
    }
}
