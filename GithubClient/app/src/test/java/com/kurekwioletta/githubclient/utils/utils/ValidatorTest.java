package com.kurekwioletta.githubclient.utils.utils;

import com.kurekwioletta.githubclient.utils.Validator;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorTest {

    private Validator mValidator;

    @Before
    public void setUp() {
        mValidator = new Validator();
    }

    @Test
    public void validUsername() {
        assertTrue(mValidator.isUsernameValid("a"));
        assertTrue(mValidator.isUsernameValid("abc"));
        assertTrue(mValidator.isUsernameValid("ABC"));
        assertTrue(mValidator.isUsernameValid("Abc"));
        assertTrue(mValidator.isUsernameValid("a-b"));
        assertTrue(mValidator.isUsernameValid("a-b-c"));
        assertTrue(mValidator.isUsernameValid("0"));
        assertTrue(mValidator.isUsernameValid("10"));
        assertTrue(mValidator.isUsernameValid("1-2"));
        assertTrue(mValidator.isUsernameValid("abc123"));
        assertTrue(mValidator.isUsernameValid("abc-123"));
        assertTrue(mValidator.isUsernameValid(StringUtils.repeat("a", 39)));
    }

    @Test
    public void invalidUsername() {
        assertFalse(mValidator.isUsernameValid(""));
        assertFalse(mValidator.isUsernameValid(" "));
        assertFalse(mValidator.isUsernameValid("a b"));
        assertFalse(mValidator.isUsernameValid("a "));
        assertFalse(mValidator.isUsernameValid(" b"));
        assertFalse(mValidator.isUsernameValid("a-"));
        assertFalse(mValidator.isUsernameValid("-b"));
        assertFalse(mValidator.isUsernameValid("a--b"));
        assertFalse(mValidator.isUsernameValid("a_b"));
        assertFalse(mValidator.isUsernameValid("a\nb"));
        assertFalse(mValidator.isUsernameValid("%"));
        assertFalse(mValidator.isUsernameValid(StringUtils.repeat("a", 40)));
    }
}
