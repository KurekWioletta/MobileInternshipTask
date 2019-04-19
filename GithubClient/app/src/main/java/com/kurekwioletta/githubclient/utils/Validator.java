package com.kurekwioletta.githubclient.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    /**
     * Validate username with regular expression.
     *
     * @param username may only contain alphanumeric characters or hyphens,
     *                 cannot have multiple consecutive hyphens,
     *                 cannot begin or end with a hyphen,
     *                 max 39 characters.
     */
    public boolean isUsernameValid(String username) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9](-?[a-zA-Z0-9]){0,38}");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
