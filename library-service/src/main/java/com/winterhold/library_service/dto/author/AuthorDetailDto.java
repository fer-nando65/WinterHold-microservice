package com.winterhold.library_service.dto.author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDetailDto {
    private String fullName;
    private String birthDate;
    private String deceaseDate;
    private String education;
    private String summary;
    List<AuthorBookDetailDto> listBook;
}
