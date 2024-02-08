package com.giftandgo.fileprocessor;

import java.util.regex.Pattern;

public class IDValidator {

    private static final String UUID_REGEX =
            "\\d[A-Z]\\d[A-Z]\\d{2}";

    private static final Pattern UUID_PATTERN = Pattern.compile(UUID_REGEX);

    public static boolean isValid(String uuid) {
        return UUID_PATTERN.matcher(uuid).matches();
    }

}
