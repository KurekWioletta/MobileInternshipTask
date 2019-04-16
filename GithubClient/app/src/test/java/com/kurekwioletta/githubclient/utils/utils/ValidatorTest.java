package com.kurekwioletta.githubclient.utils.utils;

import com.kurekwioletta.githubclient.utils.Validator;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {

    @Test
    public void validUsername() {
        Assert.assertTrue(Validator.isUsernameValid("a"));
        Assert.assertTrue(Validator.isUsernameValid("abc"));
        Assert.assertTrue(Validator.isUsernameValid("ABC"));
        Assert.assertTrue(Validator.isUsernameValid("Abc"));
        Assert.assertTrue(Validator.isUsernameValid("a-b"));
        Assert.assertTrue(Validator.isUsernameValid("a-b-c"));
        Assert.assertTrue(Validator.isUsernameValid("0"));
        Assert.assertTrue(Validator.isUsernameValid("10"));
        Assert.assertTrue(Validator.isUsernameValid("1-2"));
        Assert.assertTrue(Validator.isUsernameValid("abc123"));
        Assert.assertTrue(Validator.isUsernameValid("abc-123"));
        Assert.assertTrue(Validator.isUsernameValid(StringUtils.repeat("a", 39)));
    }

    @Test
    public void invalidUsername() {
        Assert.assertFalse(Validator.isUsernameValid(""));
        Assert.assertFalse(Validator.isUsernameValid(" "));
        Assert.assertFalse(Validator.isUsernameValid("a b"));
        Assert.assertFalse(Validator.isUsernameValid("a "));
        Assert.assertFalse(Validator.isUsernameValid(" b"));
        Assert.assertFalse(Validator.isUsernameValid("a-"));
        Assert.assertFalse(Validator.isUsernameValid("-b"));
        Assert.assertFalse(Validator.isUsernameValid("a--b"));
        Assert.assertFalse(Validator.isUsernameValid("a_b"));
        Assert.assertFalse(Validator.isUsernameValid("a\nb"));
        Assert.assertFalse(Validator.isUsernameValid("%"));
        Assert.assertFalse(Validator.isUsernameValid(StringUtils.repeat("a", 40)));
    }
}
