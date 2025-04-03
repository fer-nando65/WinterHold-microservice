package com.winterhold.library_service.service.inf;

import com.winterhold.library_service.dto.author.AuthorDetailDto;
import com.winterhold.library_service.dto.author.AuthorIndexDto;
import com.winterhold.library_service.dto.author.AuthorUpsertDto;
import com.winterhold.library_service.dto.option.OptionDto;
import com.winterhold.library_service.entity.Author;

import java.util.List;

public interface AuthorService {
    List<AuthorIndexDto> index(String name, Integer page);
    Integer getTotalPages(String name);
    AuthorUpsertDto getUpsertAuthor(Long id);
    List<OptionDto> buildTitleSelection(String paramTitle);
    void save(AuthorUpsertDto dto);
    void delete(Long id);
    String getAuthorName(Long id);
    List<Author> requestAuthors();
    AuthorDetailDto getDetail(Long id);
}
