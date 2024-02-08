package com.giftandgo.fileprocessor.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentValidatorTest {

    private final ContentValidator contentValidator = new ContentValidator();

    @Test
    void shouldPassValidationWithCorrectContent() {
        String target = "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1";
        Errors errors = new BeanPropertyBindingResult(target, "content");

        contentValidator.validate(target, errors);

        assertThat(errors.hasErrors()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2"})
    void shouldFailValidationWithInvalidNumbersOfFields(String target) {
        Errors errors = new BeanPropertyBindingResult(target, "content");

        contentValidator.validate(target, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getAllErrors()).hasAtLeastOneElementOfType(ObjectError.class);
        assertThat(errors.getAllErrors().get(0).getDefaultMessage()).isEqualTo("Some expected fields value are missing");
    }

    @Test
    void shouldFailValidationWithInvalidFieldValues() {
        String target = "invalid_uuid|invalid_id|John Smith|Likes Apricots|Rides A Bike|invalid_avgSpeed|invalidTopSpeed";
        Errors errors = new BeanPropertyBindingResult(target, "content");

        contentValidator.validate(target, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getAllErrors()).hasAtLeastOneElementOfType(ObjectError.class);
        assertThat(errors.getAllErrors().get(0).getDefaultMessage()).isEqualTo("UUID is invalid");
        assertThat(errors.getAllErrors().get(1).getDefaultMessage()).isEqualTo("ID is invalid");
        assertThat(errors.getAllErrors().get(2).getDefaultMessage()).isEqualTo("AVG speed is invalid");
        assertThat(errors.getAllErrors().get(3).getDefaultMessage()).isEqualTo("Top speed is invalid");
    }

}
