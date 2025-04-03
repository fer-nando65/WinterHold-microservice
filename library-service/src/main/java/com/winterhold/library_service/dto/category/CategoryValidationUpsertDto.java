package com.winterhold.library_service.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryValidationUpsertDto {
    private String errorCategoryName;
    private String errorFloor;
    private String errorIsle;
    private String errorBay;
}
