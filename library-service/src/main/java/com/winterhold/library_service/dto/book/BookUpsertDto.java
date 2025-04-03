package com.winterhold.library_service.dto.book;

import com.winterhold.library_service.dto.option.OptionDto;
import com.winterhold.library_service.validation.inf.UniqueBookCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UniqueBookCode(first = "code", second = "formFlag")
public class BookUpsertDto {
    @NotBlank(message = "Please provide book code")
    private String code;
    @NotBlank(message = "Please provide book title")
    private String bookTitle;
    private String categoryName;
    @NotNull(message = "Please provide the author")
    private Long authorId;
    private Boolean isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;
    private String summary;
    List<OptionDto> listOptionAuthor;
    private String formFlag;
}
