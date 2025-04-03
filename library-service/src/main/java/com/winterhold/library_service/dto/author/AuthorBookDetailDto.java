package com.winterhold.library_service.dto.author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorBookDetailDto {
    private String bookTitle;
    private String categoryName;
    private String isBorrowed;
    private String releaseDate;
    private String totalPages;
    private String isDeleted;
}
