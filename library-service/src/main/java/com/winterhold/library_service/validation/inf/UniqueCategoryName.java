package com.winterhold.library_service.validation.inf;

import com.winterhold.library_service.validation.implementation.UniqueCategoryNameImplementation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UniqueCategoryNameImplementation.class)
public @interface UniqueCategoryName {
    public String first();
    public String second();
    public String message() default "The category name is already registered";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
