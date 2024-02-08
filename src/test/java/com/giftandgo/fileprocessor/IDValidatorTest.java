package com.giftandgo.fileprocessor;

import com.giftandgo.fileprocessor.validator.IDValidator;
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
        assertThat(IDValidator.isValid("3X3D3F")).isFalse();
        assertThat(IDValidator.isValid("323535")).isFalse();
        assertThat(IDValidator.isValid("323D35")).isFalse();
        assertThat(IDValidator.isValid("D23D35")).isFalse();
    }
}
