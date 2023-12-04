package com.intuit.apigateway.complaintservice.annotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.UUID;

public class UUIDPatternValidator implements ConstraintValidator<UUIDPattern, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            if(value == null || value.isEmpty())
            {
                return true;
            }
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}