package com.winterhold.library_service.validation.implementation;

import com.winterhold.library_service.service.inf.CategoryService;
import com.winterhold.library_service.validation.inf.UniqueCategoryName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

@RequiredArgsConstructor
public class UniqueCategoryNameImplementation implements ConstraintValidator<UniqueCategoryName, Object> {

    private final CategoryService service;
    private String first;
    private String second;

    @Override
    public void initialize(UniqueCategoryName constraintAnnotation) {
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        var categoryName = (String) new BeanWrapperImpl(object).getPropertyValue(this.first);
        var formFlag = (String) new BeanWrapperImpl(object).getPropertyValue(this.second);
        return !(service.isCategoryNameTaken(categoryName) && Objects.equals(formFlag, "0"));
    }
}
