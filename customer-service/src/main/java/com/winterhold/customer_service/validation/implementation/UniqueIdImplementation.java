package com.winterhold.customer_service.validation.implementation;

import com.winterhold.customer_service.service.inf.CustomerService;
import com.winterhold.customer_service.validation.inf.UniqueId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

@RequiredArgsConstructor
public class UniqueIdImplementation implements ConstraintValidator<UniqueId, Object> {
    private final CustomerService service;
    private String first;
    private String second;

    @Override
    public void initialize(UniqueId constraintAnnotation) {
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        String id = (String) new BeanWrapperImpl(object).getPropertyValue(this.first);
        String flag = (String) new BeanWrapperImpl(object).getPropertyValue(this.second);
        return !(service.isIdTaken(id) && Objects.equals(flag, "0"));
    }
}
