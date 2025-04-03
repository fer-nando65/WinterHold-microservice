package com.winterhold.library_service.dto.category;

import com.winterhold.library_service.validation.inf.UniqueCategoryName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UniqueCategoryName(first = "categoryName", second = "formFlag")
public class CategoryUpsertDto {
    @NotBlank(message = "Please provide the category name")
    private String categoryName;
    @NotNull(message = "Please provide the floor number")
    @Min(value = 1, message = "The floor is invalid")
    private Integer floor;
    @NotBlank(message = "Please provide the isle")
    private String isle;
    @NotBlank(message = "Please provide the bay")
    private String bay;
    private String formFlag;
}
