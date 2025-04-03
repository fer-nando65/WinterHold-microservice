package com.winterhold.library_service.validation.implementation;

import com.winterhold.library_service.service.inf.BookService;
import com.winterhold.library_service.validation.inf.UniqueBookCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

@RequiredArgsConstructor
public class UniqueBookCodeImplementation implements ConstraintValidator<UniqueBookCode, Object> {

    private final BookService service;
    private String first;
    private String second;

    @Override
    public void initialize(UniqueBookCode constraintAnnotation) {
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        String code = (String) new BeanWrapperImpl(object).getPropertyValue(this.first);
        String formFlag = (String) new BeanWrapperImpl(object).getPropertyValue(this.second);
        return !(service.isCodeTaken(code) && Objects.equals(formFlag, "0"));
    }
}
