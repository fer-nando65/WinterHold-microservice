package com.winterhold.loan_service.dto.loan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanValidationDto {
    private String errorCustomer;
    private String errorBook;
    private String errorLoanDate;
}
