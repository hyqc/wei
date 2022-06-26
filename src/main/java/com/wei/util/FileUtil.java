package com.wei.util;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

/**
 * @author Administrator
 */
@Component
public class FileUtil {

    public static final Long KB = 1024L;
    public static final Long M = 1024L * KB;
    public static final Long G = 1024L * M;

    public static final String KB_UNIT = "KB";
    public static final String M_UNIT = "M";
    public static final String G_UNIT = "G";

    public static String convertBitSizeToString(Long size) {
        DecimalFormat df = new DecimalFormat("#");
        if (size < M) {
            return df.format(size / KB) + KB_UNIT;
        }
        if (size < G) {
            return df.format(size / M) + M_UNIT;
        }
        return df.format(size / G) + G_UNIT;
    }

    public static String convertBitSizeToString(Long size, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        if (size < M) {
            return df.format(size / KB) + KB_UNIT;
        }
        if (size < G) {
            return df.format(size / M) + M_UNIT;
        }
        return df.format(size / G) + G_UNIT;
    }
}
