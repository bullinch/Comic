package com.example.onlinecomic.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static long parseStringToLong(String string) {
        String regEx = "[^0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(string);
        return Long.parseLong(matcher.replaceAll("").trim());
    }
}
