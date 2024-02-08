package com.giftandgo.fileprocessor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IDValidatorTest {

    @Test
    void shouldValidateID() {
        assertThat(IDValidator.isValid("1X1D14")).isTrue();
        assertThat(IDValidator.isValid("2X2D24")).isTrue();
        assertThat(IDValidator.isValid("3X3D35")).isTrue();
    }

    @Test
    void shouldFailWithInvalidUUID() {
        assertThat(IDValidator.isValid("")).isFalse();
    }
}
