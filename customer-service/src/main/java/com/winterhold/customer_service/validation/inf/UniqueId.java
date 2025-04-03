package com.winterhold.customer_service.validation.inf;

import com.winterhold.customer_service.validation.implementation.UniqueIdImplementation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UniqueIdImplementation.class)
public @interface UniqueId {
    public String first();
    public String second();
    public String message() default "The membership number is already taken";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
