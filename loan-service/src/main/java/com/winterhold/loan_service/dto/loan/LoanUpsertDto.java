package com.winterhold.loan_service.dto.loan;

import com.winterhold.loan_service.dto.option.OptionDto;
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
public class LoanUpsertDto {
    private Long id;
    @NotBlank(message = "Please choose the customer")
    private String customerId;
    @NotBlank(message = "Please choose the book")
    private String bookCode;
    @NotNull(message = "Please provide the loan date")
    private LocalDate loanDate;
    private String note;
    List<OptionDto> listCustomer;
    List<OptionDto> listBook;
}
