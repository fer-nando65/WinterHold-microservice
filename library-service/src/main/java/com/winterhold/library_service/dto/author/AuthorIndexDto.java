package com.winterhold.library_service.dto.author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorIndexDto {
    private Long id;
    private String fullName;
    private Integer age;
    private String status;
    private String education;
    private Boolean hasBook;
}
