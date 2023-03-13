package com.formlaez.infrastructure.util;

import java.nio.charset.StandardCharsets;

public class CsvUtils {
    public static final String UTF8_BOM = new String(new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}, StandardCharsets.UTF_8);
}
