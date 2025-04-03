package com.winterhold.library_service.validation.implementation;

import com.winterhold.library_service.validation.inf.ValidBirthDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidBirthDateImplementation implements ConstraintValidator<ValidBirthDate, LocalDate> {

    @Override
    public void initialize(ValidBirthDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null){
            return true;
        }

        return date.isBefore(LocalDate.now());
    }
}
