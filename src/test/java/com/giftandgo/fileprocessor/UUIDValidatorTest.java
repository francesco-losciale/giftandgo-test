package com.giftandgo.fileprocessor;

import com.giftandgo.fileprocessor.validator.UUIDValidator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UUIDValidatorTest {

    @Test
    void shouldValidateUUID() {
        assertThat(UUIDValidator.isValid("123e4567-e89b-12d3-a456-556642440000")).isTrue();
    }

    @Test
    void shouldFailValidationOfInvalidUUID() {
        assertThat(UUIDValidator.isValid("Invalid UUID")).isFalse();
    }
}
