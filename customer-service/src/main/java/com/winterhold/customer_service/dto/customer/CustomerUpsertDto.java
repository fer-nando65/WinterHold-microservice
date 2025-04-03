package com.winterhold.customer_service.dto.customer;

import com.winterhold.customer_service.dto.option.OptionDto;
import com.winterhold.customer_service.validation.inf.UniqueId;
import com.winterhold.customer_service.validation.inf.ValidBirthDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
@UniqueId(first = "id", second = "formFlag")
public class CustomerUpsertDto {
    @NotBlank(message = "Please provide membership number")
    private String id;
    @NotBlank(message = "Please provide your first name")
    private String firstName;
    private String lastName;
    @NotNull(message = "Please provide your birth date")
    @ValidBirthDate
    private LocalDate birthDate;
    @NotNull(message = "Please provide your gender")
    private String gender;
    private String phone;
    private String address;
    List<OptionDto> genderRadio;

    private String formFlag;
}
