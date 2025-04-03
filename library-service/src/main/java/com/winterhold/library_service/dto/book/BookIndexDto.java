package com.winterhold.library_service.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookIndexDto {
    private String code;
    private String bookTitle;
    private String authorName;
    private String isBorrowed;
    private String releaseDate;
    private String summary;
    private String categoryName;
    private Integer totalPage;
}
