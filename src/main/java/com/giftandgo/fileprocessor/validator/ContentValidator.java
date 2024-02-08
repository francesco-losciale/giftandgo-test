package com.giftandgo.fileprocessor.validator;

import com.giftandgo.fileprocessor.validator.IDValidator;
import com.giftandgo.fileprocessor.validator.UUIDValidator;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ContentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var content = (String) target;
        String[] fields = content.split("\\|");

        if (fields.length != 7) {
            errors.reject("missing_fields", "Some expected fields value are missing");
            return;
        }

        if (!UUIDValidator.isValid(fields[0])) {
            errors.reject("invalid_uuid", "UUID is invalid");
        }

        if (!IDValidator.isValid(fields[1])) {
            errors.reject("invalid_id", "ID is invalid");
        }

        if (!NumberUtils.isParsable(fields[5])) {
            errors.reject("invalid_avg_speed", "AVG speed is invalid");
        }

        if (!NumberUtils.isParsable(fields[6])) {
            errors.reject("invalid_top_speed", "Top speed is invalid");
        }

    }
}
