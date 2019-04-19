package com.kurekwioletta.githubclient.utils.utils;

import com.kurekwioletta.githubclient.utils.Validator;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {

    private Validator mMockedValidator;

    @Before
    public void setUp() {
        mMockedValidator = new Validator();
    }

    @Test
    public void validUsername() {
        Assert.assertTrue(mMockedValidator.isUsernameValid("a"));
        Assert.assertTrue(mMockedValidator.isUsernameValid("abc"));
        Assert.assertTrue(mMockedValidator.isUsernameValid("ABC"));
        Assert.assertTrue(mMockedValidator.isUsernameValid("Abc"));
        Assert.assertTrue(mMockedValidator.isUsernameValid("a-b"));
        Assert.assertTrue(mMockedValidator.isUsernameValid("a-b-c"));
        Assert.assertTrue(mMockedValidator.isUsernameValid("0"));
        Assert.assertTrue(mMockedValidator.isUsernameValid("10"));
        Assert.assertTrue(mMockedValidator.isUsernameValid("1-2"));
        Assert.assertTrue(mMockedValidator.isUsernameValid("abc123"));
        Assert.assertTrue(mMockedValidator.isUsernameValid("abc-123"));
        Assert.assertTrue(mMockedValidator.isUsernameValid(StringUtils.repeat("a", 39)));
    }

    @Test
    public void invalidUsername() {
        Assert.assertFalse(mMockedValidator.isUsernameValid(""));
        Assert.assertFalse(mMockedValidator.isUsernameValid(" "));
        Assert.assertFalse(mMockedValidator.isUsernameValid("a b"));
        Assert.assertFalse(mMockedValidator.isUsernameValid("a "));
        Assert.assertFalse(mMockedValidator.isUsernameValid(" b"));
        Assert.assertFalse(mMockedValidator.isUsernameValid("a-"));
        Assert.assertFalse(mMockedValidator.isUsernameValid("-b"));
        Assert.assertFalse(mMockedValidator.isUsernameValid("a--b"));
        Assert.assertFalse(mMockedValidator.isUsernameValid("a_b"));
        Assert.assertFalse(mMockedValidator.isUsernameValid("a\nb"));
        Assert.assertFalse(mMockedValidator.isUsernameValid("%"));
        Assert.assertFalse(mMockedValidator.isUsernameValid(StringUtils.repeat("a", 40)));
    }
}
