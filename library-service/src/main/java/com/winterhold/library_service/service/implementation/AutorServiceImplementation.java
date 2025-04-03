package com.winterhold.library_service.service.implementation;

import com.winterhold.library_service.dto.author.AuthorBookDetailDto;
import com.winterhold.library_service.dto.author.AuthorDetailDto;
import com.winterhold.library_service.dto.author.AuthorIndexDto;
import com.winterhold.library_service.dto.author.AuthorUpsertDto;
import com.winterhold.library_service.dto.option.OptionDto;
import com.winterhold.library_service.entity.Author;
import com.winterhold.library_service.entity.Book;
import com.winterhold.library_service.helper.CustomHelper;
import com.winterhold.library_service.repository.AuthorRepository;
import com.winterhold.library_service.service.inf.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AutorServiceImplementation implements AuthorService {
    private final AuthorRepository repository;
    private final int rowsInPage = 8;

    @Override
    public List<AuthorIndexDto> index(String name, Integer page){
        Pageable pagination = PageRequest.of(page - 1, rowsInPage);
        var authors = repository.getAll(name, pagination);
        return authors.stream().map(this::mapToAuthorIndexDto).toList();
    }

    private AuthorIndexDto mapToAuthorIndexDto(Author author){
        return AuthorIndexDto.builder()
                .id(author.getId())
                .fullName(CustomHelper.BuildFullName(author.getTitle(), author.getFirstName(), author.getLastName()))
                .age(CustomHelper.AgeCounter(author.getBirthDate()))
                .status(CustomHelper.CheckStatus(author.getDeceasedDate()))
                .education(author.getEducation())
                .hasBook(!author.getBook().isEmpty())
                .build();
    }

    @Override
    public Integer getTotalPages(String name){
        double totalRows = this.repository.count(name);
        return (int)(Math.ceil(totalRows/rowsInPage));
    }

    @Override
    public AuthorUpsertDto getUpsertAuthor(Long id){
        if(id != 0){
            var selectedAuthor = repository.getById(id);
            return AuthorUpsertDto.builder()
                    .id(selectedAuthor.getId())
                    .title(selectedAuthor.getTitle())
                    .firstName(selectedAuthor.getFirstName())
                    .lastName(selectedAuthor.getLastName())
                    .birthDate(selectedAuthor.getBirthDate())
                    .deceaseDate(selectedAuthor.getDeceasedDate())
                    .education(selectedAuthor.getEducation())
                    .summary(selectedAuthor.getSummary())
                    .optionTitle(buildTitleSelection(selectedAuthor.getTitle()))
                    .build();
        }else {
            return AuthorUpsertDto.builder()
                    .optionTitle(buildTitleSelection(""))
                    .build();
        }
    }

    @Override
    public List<OptionDto> buildTitleSelection(String paramTitle){
        List<OptionDto> optionTitle = new LinkedList<>();
        var titles = Arrays.asList("mr", "mrs", "miss", "ms");
        for(var title : titles){
            optionTitle.add(OptionDto.builder()
                    .text(title.substring(0,1).toUpperCase() + title.substring(1).toLowerCase())
                    .value(title)
                    .selected(title.equals(paramTitle))
                    .build());
        }

        return optionTitle;
    }

    @Override
    public void save(AuthorUpsertDto dto){
        repository.save(Author.builder()
                        .id(dto.getId())
                        .title(dto.getTitle())
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .birthDate(dto.getBirthDate())
                        .deceasedDate(dto.getDeceaseDate())
                        .education(dto.getEducation())
                        .summary(dto.getSummary())
                .build());
    }

    @Override
    public void delete(Long id){
        repository.delete(repository.getById(id));
    }

    @Override
    public String getAuthorName(Long id){
        var selectedAuthor = repository.getById(id);
        return CustomHelper.BuildFullName(selectedAuthor.getTitle(), selectedAuthor.getFirstName(), selectedAuthor.getLastName());
    }

    @Override
    public List<Author> requestAuthors(){
        return repository.requestAuthors();
    }

    @Override
    public AuthorDetailDto getDetail(Long id){
        var selectedAuthor = repository.getById(id);
        return AuthorDetailDto.builder()
                .fullName(CustomHelper.BuildFullName(selectedAuthor.getTitle(), selectedAuthor.getFirstName(), selectedAuthor.getLastName()))
                .birthDate(selectedAuthor.getBirthDate().toString())
                .deceaseDate((selectedAuthor.getDeceasedDate() != null) ? selectedAuthor.getDeceasedDate().toString() : "-")
                .education(selectedAuthor.getEducation())
                .summary(selectedAuthor.getSummary())
                .listBook(selectedAuthor.getBook().stream().map(this::mapToAuthorBookDetailDto).toList())
                .build();
    }

    private AuthorBookDetailDto mapToAuthorBookDetailDto(Book book){
        return AuthorBookDetailDto.builder()
                .bookTitle(book.getBookTitle())
                .categoryName(book.getCategoryName())
                .isBorrowed((book.getIsBorrowed()) ? "Borrowed" : "Available")
                .releaseDate((Objects.isNull(book.getReleaseDate())) ? "-" : book.getReleaseDate().toString())
                .totalPages((Objects.isNull(book.getTotalPage())) ? "-" :book.getTotalPage().toString())
                .isDeleted((Objects.isNull(book.getIsDeleted()) ? "No" : "Yes"))
                .build();
    }
}
