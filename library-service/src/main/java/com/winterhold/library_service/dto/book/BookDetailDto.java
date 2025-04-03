package com.winterhold.library_service.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetailDto {
    private String title;
    private String categoryName;
    private String authorName;
    private Integer floor;
    private String isle;
    private String bay;
}
