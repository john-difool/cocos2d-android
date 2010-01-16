package org.cocos2d.utils;

import java.util.Locale;

public class Formatter {
    private static StringBuilder sb;
    private static java.util.Formatter formatter;

    static {
           sb = new StringBuilder();
           formatter = new java.util.Formatter(sb, Locale.US);
    }

    public static String format(java.lang.String s, java.lang.Object... objects) {
        formatter.format(s, objects);
        return sb.toString();
    }
}
