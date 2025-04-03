package com.winterhold.library_service.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto {
    private String code;
    private String bookTitle;
    private String categoryName;
    private Integer floor;
    private String isle;
    private String bay;
    private String authorName;
    private Boolean isBorrowed;
}
