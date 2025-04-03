package com.winterhold.library_service.service.inf;

import com.winterhold.library_service.dto.book.BookDetailDto;
import com.winterhold.library_service.dto.book.BookIndexDto;
import com.winterhold.library_service.dto.book.BookResponseDto;
import com.winterhold.library_service.dto.book.BookUpsertDto;
import com.winterhold.library_service.dto.option.OptionDto;

import java.util.List;

public interface BookService {
    List<BookIndexDto> index(String categoryName, String bookTitle, String authorName, String isAvailable, Integer page);
    Integer getTotalPages(String categoryName, String bookTitle, String authorName, String isAvailable);
    List<OptionDto> buildAuthorOption(Long id);
    BookUpsertDto getUpsertBook(String categoryName, String code);
    void save(BookUpsertDto dto);
    void delete(String code);
    String getSummary(String code);
    String getBookTitle(String code);
    Boolean isCodeTaken(String code);
    List<BookResponseDto> getListBookByTitle(String title);
    List<OptionDto> buildBookOption(String bookCode);
    void changeIsBorrowedBook(String firstBook, String secondBook);
    BookDetailDto getDetailBook(String code);
}
