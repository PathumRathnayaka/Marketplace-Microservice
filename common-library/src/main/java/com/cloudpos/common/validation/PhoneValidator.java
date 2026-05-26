package com.cloudpos.common.validation;

import java.util.regex.Pattern;

public final class PhoneValidator {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{7,15}$");

    private PhoneValidator() {
    }

    public static boolean isValid(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber).matches();
    }
}
