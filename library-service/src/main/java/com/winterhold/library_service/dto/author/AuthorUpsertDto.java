package com.winterhold.library_service.dto.author;

import com.winterhold.library_service.dto.option.OptionDto;
import com.winterhold.library_service.validation.inf.ValidBirthDate;
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
public class AuthorUpsertDto {
    private Long id;
    @NotBlank(message = "Please provide your title")
    private String title;
    @NotBlank(message = "Please provide your first name")
    private String firstName;
    private String lastName;
    @NotNull(message = "Please provide your birth date")
    @ValidBirthDate
    private LocalDate birthDate;
    private LocalDate deceaseDate;
    private String education;
    private String summary;
    List<OptionDto> optionTitle;
}
