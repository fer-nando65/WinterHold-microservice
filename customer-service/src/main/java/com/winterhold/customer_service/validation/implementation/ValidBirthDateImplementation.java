package com.winterhold.customer_service.validation.implementation;

import com.winterhold.customer_service.validation.inf.ValidBirthDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidBirthDateImplementation implements ConstraintValidator<ValidBirthDate, LocalDate> {

    @Override
    public void initialize(ValidBirthDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if(birthDate == null){
            return true;
        }
        return birthDate.isBefore(LocalDate.now());
    }
}
