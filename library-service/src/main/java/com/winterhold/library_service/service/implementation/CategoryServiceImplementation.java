package com.winterhold.library_service.service.implementation;

import com.winterhold.library_service.dto.category.CategoryIndexDto;
import com.winterhold.library_service.dto.category.CategoryUpsertDto;
import com.winterhold.library_service.dto.category.CategoryValidationUpsertDto;
import com.winterhold.library_service.entity.Category;
import com.winterhold.library_service.repository.CategoryRepository;
import com.winterhold.library_service.service.inf.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository repository;
    private final Integer rowsInPage = 8;

    @Override
    public List<CategoryIndexDto> getAll(String categoryName, Integer page){
        Pageable pagination = PageRequest.of(page - 1, rowsInPage);
        var categories = repository.getAll(categoryName, pagination);
        return categories.stream().map(this::mapToCategoryIndexDto).toList();
    }

    private CategoryIndexDto mapToCategoryIndexDto(Category category){
        Integer countBook = 0;
        for(var book : category.getBook()){
            if(Objects.isNull(book.getIsDeleted())){
                countBook++;
            }
        }
        return CategoryIndexDto.builder()
                .categoryName(category.getCategoryName())
                .floor(category.getFloor())
                .isle(category.getIsle())
                .bay(category.getBay())
                .totalBooks(countBook)
                .build();
    }

    @Override
    public CategoryUpsertDto getUpsertCategory(String categoryName){
        if(!categoryName.equals("-1")){
            var selectedCategory = repository.getById(categoryName);
            return CategoryUpsertDto.builder()
                    .categoryName(selectedCategory.getCategoryName())
                    .floor(selectedCategory.getFloor())
                    .isle(selectedCategory.getIsle())
                    .bay(selectedCategory.getBay())
                    .formFlag("1")
                    .build();
        }else {
            return CategoryUpsertDto.builder()
                    .formFlag("0")
                    .build();
        }
    }

    @Override
    public void save(CategoryUpsertDto dto){
        repository.save(Category.builder()
                        .categoryName(dto.getCategoryName())
                        .floor(dto.getFloor())
                        .isle(dto.getIsle())
                        .bay(dto.getBay())
                .build());
    }

    @Override
    public Integer getTotalPages(String CategoryName){
        double totalRows = repository.count(CategoryName);
        return (int)(Math.ceil(totalRows/rowsInPage));
    }

    @Override
    public void delete(String categoryName){
        repository.delete(repository.getById(categoryName));
    }

    @Override
    public CategoryValidationUpsertDto getErrorMessagesValidation(BindingResult validation){
        var dto = new CategoryValidationUpsertDto();
        if(validation.hasFieldErrors()){
            for(var error : validation.getFieldErrors()){
                if(Objects.equals(error.getField(), "categoryName")){
                    dto.setErrorCategoryName(error.getDefaultMessage());
                }else if(Objects.equals(error.getField(), "floor")){
                    dto.setErrorFloor(error.getDefaultMessage());
                }else if(Objects.equals(error.getField(), "isle")){
                    dto.setErrorIsle(error.getDefaultMessage());
                }else {
                    dto.setErrorBay(error.getDefaultMessage());
                }
            }
        }

        if(validation.hasGlobalErrors()){
            dto.setErrorCategoryName(Objects.requireNonNull(validation.getGlobalError()).getDefaultMessage());
        }
        return dto;
    }

    @Override
    public Boolean isCategoryNameTaken(String categoryName){
        return repository.existsById(categoryName);
    }
}
