package com.winterhold.library_service.validation.inf;

import com.winterhold.library_service.validation.implementation.ValidBirthDateImplementation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidBirthDateImplementation.class)
public @interface ValidBirthDate {
    public String message() default "Your birth date is invalid";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
