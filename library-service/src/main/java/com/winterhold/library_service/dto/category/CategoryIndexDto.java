package com.winterhold.library_service.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryIndexDto {
    private String categoryName;
    private Integer floor;
    private String isle;
    private String bay;
    private Integer totalBooks;
}
