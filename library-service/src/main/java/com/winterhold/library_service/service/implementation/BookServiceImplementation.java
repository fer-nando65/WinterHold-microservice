package com.winterhold.library_service.service.implementation;

import com.winterhold.library_service.dto.book.BookDetailDto;
import com.winterhold.library_service.dto.book.BookIndexDto;
import com.winterhold.library_service.dto.book.BookResponseDto;
import com.winterhold.library_service.dto.book.BookUpsertDto;
import com.winterhold.library_service.dto.option.OptionDto;
import com.winterhold.library_service.entity.Book;
import com.winterhold.library_service.helper.CustomHelper;
import com.winterhold.library_service.repository.BookRepository;
import com.winterhold.library_service.service.inf.AuthorService;
import com.winterhold.library_service.service.inf.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {

    private final BookRepository repository;
    private final Integer rowsInPage = 8;
    private final AuthorService authorService;

    @Override
    public List<BookIndexDto> index(String categoryName, String bookTitle, String authorName,
                                    String isAvailable, Integer page){
        Pageable pagination = PageRequest.of(page - 1, rowsInPage);
        List<Book> books;
        if(isAvailable.equals("on")){
            books = repository.getAll(categoryName, bookTitle, authorName, isAvailable, pagination);
        }else {
            books = repository.getAll(categoryName, bookTitle, authorName, pagination);
        }
        return books.stream().map(this::mapToBookIndexDto).toList();
    }

    private BookIndexDto mapToBookIndexDto(Book book){
        return BookIndexDto.builder()
                .code(book.getCode())
                .bookTitle(book.getBookTitle())
                .authorName(CustomHelper.BuildFullName(book.getAuthor().getTitle(), book.getAuthor().getFirstName(), book.getAuthor().getLastName()))
                .isBorrowed((book.getIsBorrowed()) ? "Borrowed" : "Available")
                .releaseDate(book.getReleaseDate().toString())
                .summary(book.getSummary())
                .categoryName(book.getCategoryName())
                .totalPage(book.getTotalPage())
                .build();
    }

    @Override
    public Integer getTotalPages(String categoryName, String bookTitle, String authorName,
                                 String isAvailable){
        double totalRows;
        if(isAvailable.equals("on")){
            totalRows = repository.count(categoryName, bookTitle, authorName, isAvailable);
        }else {
            totalRows = repository.count(categoryName, bookTitle, authorName);
        }
        return (int)(Math.ceil(totalRows/rowsInPage));
    }

    @Override
    public BookUpsertDto getUpsertBook(String categoryName, String code){
        if(!code.isEmpty()){
            var selectedBook = repository.getById(code);
            return BookUpsertDto.builder()
                    .code(selectedBook.getCode())
                    .bookTitle(selectedBook.getBookTitle())
                    .categoryName(selectedBook.getCategoryName())
                    .authorId(selectedBook.getAuthorId())
                    .releaseDate(selectedBook.getReleaseDate())
                    .summary(selectedBook.getSummary())
                    .totalPage(selectedBook.getTotalPage())
                    .isBorrowed(selectedBook.getIsBorrowed())
                    .listOptionAuthor(buildAuthorOption(selectedBook.getAuthorId()))
                    .formFlag("1")
                    .build();
        }else {
            return BookUpsertDto.builder()
                    .categoryName(categoryName)
                    .isBorrowed(false)
                    .listOptionAuthor(buildAuthorOption((long) 0))
                    .formFlag("0")
                    .build();
        }
    }

    @Override
    public List<OptionDto> buildAuthorOption(Long id){
        var authors = authorService.requestAuthors();
        List<OptionDto> listOptionAuthor = new LinkedList<>();
        for(var author : authors){
            listOptionAuthor.add(OptionDto.builder()
                            .text(CustomHelper.BuildFullName(author.getTitle(), author.getFirstName(), author.getLastName()))
                            .value(author.getId().toString())
                            .selected(Objects.equals(id, author.getId()))
                    .build());
        }

        return listOptionAuthor;
    }

    @Override
    public void save(BookUpsertDto dto){
        repository.save(Book.builder()
                        .code(dto.getCode())
                        .bookTitle(dto.getBookTitle())
                        .categoryName(dto.getCategoryName())
                        .authorId(dto.getAuthorId())
                        .isBorrowed(dto.getIsBorrowed())
                        .summary(dto.getSummary())
                        .releaseDate(dto.getReleaseDate())
                        .totalPage(dto.getTotalPage())
                .build());
    }

    @Override
    public void delete(String code){
        var selectedBook = repository.getById(code);
        selectedBook.setIsDeleted(LocalDate.now());
        repository.save(selectedBook);
    }

    @Override
    public String getSummary(String code){
        return repository.getById(code).getSummary();
    }

    @Override
    public String getBookTitle(String code){
        return repository.getById(code).getBookTitle();
    }

    @Override
    public Boolean isCodeTaken(String code){
        return repository.existsById(code);
    }

    @Override
    public List<BookResponseDto> getListBookByTitle(String title){
        var books = repository.getListBookByTitle(title);
        return books.stream().map(this::mapToBookResponseDto).toList();
    }

    private BookResponseDto mapToBookResponseDto(Book book){
        return BookResponseDto.builder()
                .code(book.getCode())
                .bookTitle(book.getBookTitle())
                .categoryName(book.getCategory().getCategoryName())
                .floor(book.getCategory().getFloor())
                .isle(book.getCategory().getIsle())
                .bay(book.getCategory().getBay())
                .authorName(CustomHelper.BuildFullName(
                        book.getAuthor().getTitle(),
                        book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()
                ))
                .isBorrowed(book.getIsBorrowed())
                .build();
    }

    @Override
    public List<OptionDto> buildBookOption(String bookCode){
        var optBook = new LinkedList<OptionDto>();
        var lisBook = repository.getBooks();
        for(var book : lisBook){
            optBook.add(OptionDto.builder()
                    .text(book.getBookTitle())
                    .value(book.getCode())
                    .selected(Objects.equals(book.getCode(), bookCode))
                    .build());
        }
        if(!Objects.equals(bookCode, "-1")){
            var selectedBook = repository.getById(bookCode);
            optBook.add(OptionDto.builder()
                            .text(selectedBook.getBookTitle())
                            .value(selectedBook.getCode())
                            .selected(true)
                    .build());
        }
        return optBook;
    }

    @Override
    public void changeIsBorrowedBook(String firstBook, String secondBook){
        if(!Objects.equals(firstBook, "")){
            var bookOne = repository.getById(firstBook);
            bookOne.setIsBorrowed(true);
            repository.save(bookOne);
        }

        if(!Objects.equals(secondBook, "")){
            var bookTwo = repository.getById(secondBook);
            bookTwo.setIsBorrowed(false);
            repository.save(bookTwo);
        }
    }

    @Override
    public BookDetailDto getDetailBook(String code){
        var selectedBook = repository.getById(code);
        if(!Objects.isNull(selectedBook)){
            return BookDetailDto.builder()
                    .title(selectedBook.getBookTitle())
                    .categoryName(selectedBook.getCategoryName())
                    .authorName(CustomHelper.BuildFullName(selectedBook.getAuthor().getTitle(), selectedBook.getAuthor().getFirstName(), selectedBook.getAuthor().getLastName()))
                    .floor(selectedBook.getCategory().getFloor())
                    .isle(selectedBook.getCategory().getIsle())
                    .bay(selectedBook.getCategory().getBay())
                    .build();
        }else {
            return null;
        }
    }
}
