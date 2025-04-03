package com.winterhold.library_service.service.inf;

import com.winterhold.library_service.dto.category.CategoryIndexDto;
import com.winterhold.library_service.dto.category.CategoryUpsertDto;
import com.winterhold.library_service.dto.category.CategoryValidationUpsertDto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CategoryService {
    List<CategoryIndexDto> getAll(String categoryName, Integer page);
    CategoryUpsertDto getUpsertCategory(String categoryName);
    void save(CategoryUpsertDto dto);
    Integer getTotalPages(String categoryName);
    void delete(String categoryName);
    CategoryValidationUpsertDto getErrorMessagesValidation(BindingResult validation);
    Boolean isCategoryNameTaken(String categoryName);
}
